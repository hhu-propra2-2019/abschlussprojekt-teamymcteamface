package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.paging.ThreadPage;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import mops.foren.infrastructure.web.TopicForm;
import mops.foren.infrastructure.web.ValidationService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static mops.foren.infrastructure.web.ValidationService.*;

@Controller
@SessionScope
@RequestMapping("/foren/topic")
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class TopicController {

    private ForumService forumService;
    private ThreadService threadService;
    private UserService userService;
    private KeycloakService keycloakService;
    private ValidationService validationService;
    private String errorMessage;

    /**
     * Constructor for TopicController. The parameters are injected.
     *
     * @param forumService      - injected ForumService (ApplicationService)
     * @param threadService     - ThreadService (ApplicationService)
     * @param userService       - UserService (ApplicationService)
     * @param keycloakService   - KeycloakService (Infrastructure Service)
     * @param validationService - VerificationService (Infrastructure Service)
     */
    public TopicController(ForumService forumService,
                           ThreadService threadService,
                           UserService userService,
                           KeycloakService keycloakService,
                           ValidationService validationService) {
        this.forumService = forumService;
        this.threadService = threadService;
        this.userService = userService;
        this.keycloakService = keycloakService;
        this.validationService = validationService;
    }

    /**
     * Mapping to the topic page.
     *
     * @param forenID the forum id
     * @param topicID the topic id
     * @param model   the model
     * @return The template for the threads
     */
    @GetMapping("/{forenID}/{topicID}")
    public String enterATopic(@PathVariable String forenID,
                              @PathVariable String topicID,
                              @RequestParam("page") Integer page,
                              Model model) {

        ForumId forumId = new ForumId(Long.valueOf(forenID));
        TopicId topicId = new TopicId(Long.valueOf(topicID));
        ThreadPage threadPage = this.threadService.getThreads(topicId, page - 1);

        model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
        model.addAttribute("forumId", forenID);
        model.addAttribute("topicId", topicID);
        model.addAttribute("pagingObject", threadPage.getPaging());
        model.addAttribute("threads", threadPage.getThreads());

        return "list-threads";
    }

    /**
     * Create a new topic.
     *
     * @param forenID The forum id
     * @param model   The model
     * @return The template for creating a new topic
     */
    @GetMapping("/{forenID}/new-topic")
    public String createNewTopic(@PathVariable String forenID,
                                 Model model) {
        model.addAttribute("error", this.errorMessage);
        model.addAttribute("form", new TopicForm("", "", false, false));
        model.addAttribute("forenId", forenID);
        model.addAttribute("minTitleLength", MIN_TITLE_LENGTH);
        model.addAttribute("maxTitleLength", MAX_TITLE_LENGTH);
        model.addAttribute("minDescriptionLength", MIN_DESCRIPTION_LENGTH);
        model.addAttribute("maxDescriptionLength", MAX_DESCRIPTION_LENGTH);
        return "create-topic";
    }

    /**
     * Creating a topic and redirecting to the forum page.
     *
     * @param topicForm   a form to create topics.
     * @param forumIdLong The id of the thread the post is in.
     * @return The template for the thread
     */
    @PostMapping("/new-topic")
    public String newTopic(@Valid @ModelAttribute TopicForm topicForm,
                           BindingResult bindingResult,
                           @RequestParam("forenId") Long forumIdLong) {

        // for failed validation
        if (bindingResult.hasErrors()) {
            this.errorMessage =
                    this.validationService.getErrorDescriptionFromErrorObjects(bindingResult);

            return String.format("redirect:/foren/topic/%d/new-topic", forumIdLong);
        }

        // set error message to null after successful validation
        this.errorMessage = null;

        ForumId forumId = new ForumId(forumIdLong);
        Topic topic = topicForm.getTopic(forumId);
        this.forumService.addTopicInForum(forumId, topic);
        return String.format("redirect:/foren/my-forums/%d", forumIdLong);
    }

    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenficationToken
     * @return
     */
    @ModelAttribute("account")
    public Account addAccountToTheRequest(KeycloakAuthenticationToken token) {
        if (token == null) {
            return null;
        }

        return this.keycloakService.createAccountFromPrincipal(token);
    }
}
