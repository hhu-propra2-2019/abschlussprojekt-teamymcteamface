package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.TopicService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.*;
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
    private TopicService topicService;
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
                           TopicService topicService,
                           ThreadService threadService,
                           UserService userService,
                           KeycloakService keycloakService,
                           ValidationService validationService) {
        this.forumService = forumService;
        this.topicService = topicService;
        this.threadService = threadService;
        this.userService = userService;
        this.keycloakService = keycloakService;
        this.validationService = validationService;
    }

    /**
     * Mapping to the topic page.
     *
     * @param token   They keycloak token
     * @param forenID the forum id
     * @param topicID the topic id
     * @param model   the model
     * @return The template for the threads
     */
    @GetMapping("/{forenID}/{topicID}")
    public String enterATopic(KeycloakAuthenticationToken token,
                              @PathVariable String forenID,
                              @PathVariable String topicID,
                              @RequestParam("page") Integer page,
                              Model model) {
        User user = this.userService.getUserFromDB(token);
        ForumId forumId = new ForumId(Long.valueOf(forenID));
        TopicId topicId = new TopicId(Long.valueOf(topicID));
        ThreadPage visibleThreadPage =
                this.threadService.getThreadPageByVisibility(topicId, page - 1, true);
        Integer countInvisibleThreads = this.threadService.countInvisibleThreads(topicId);
        model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
        model.addAttribute("forumId", forumId);
        model.addAttribute("topicId", topicId);
        model.addAttribute("pagingObject", visibleThreadPage.getPaging());
        model.addAttribute("threads", visibleThreadPage.getThreads());
        model.addAttribute("countInvisibleThreads", countInvisibleThreads);
        model.addAttribute("moderatePermission",
                user.checkPermission(forumId, Permission.MODERATE_THREAD));
        model.addAttribute("deletePermission",
                user.checkPermission(forumId, Permission.DELETE_THREAD));

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
     * Delete a topic.
     *
     * @param token       The keycloak token
     * @param forumIdLong The forum Id
     * @param topicIdLong The topic Id
     * @return Redirect to forum mainpage or to error page
     */
    @PostMapping("/delete-topic")
    public String deleteTopic(KeycloakAuthenticationToken token,
                              @RequestParam("forenId") Long forumIdLong,
                              @RequestParam("topicId") Long topicIdLong) {
        User user = this.userService.getUserFromDB(token);
        ForumId forumId = new ForumId(forumIdLong);
        TopicId topicId = new TopicId(topicIdLong);

        if (user.checkPermission(forumId, Permission.DELETE_TOPIC)) {
            this.topicService.deleteTopic(topicId);
            return String.format("redirect:/foren/my-forums/%d", forumIdLong);
        }

        return "error-no-permission";


    }


    /**
     * Mapping to the topic page for moderation.
     *
     * @param forumIdLong the forum id
     * @param topicIdLong the topic id
     * @param page        The thread page
     * @param model       the model
     * @return The template for the threads
     */
    @PostMapping("moderationview")
    public String enterATopicAsModerator(@RequestParam("forumId") Long forumIdLong,
                                         @RequestParam("topicId") Long topicIdLong,
                                         @RequestParam("page") Integer page,
                                         Model model,
                                         KeycloakAuthenticationToken token) {
        ForumId forumId = new ForumId(forumIdLong);
        TopicId topicId = new TopicId(topicIdLong);
        User user = this.userService.getUserFromDB(token);

        if (user.checkPermission(forumId, Permission.MODERATE_THREAD)) {
            ThreadPage invisibleThreadPage =
                    this.threadService.getThreadPageByVisibility(topicId, page - 1, false);
            model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
            model.addAttribute("forumId", forumId);
            model.addAttribute("topicId", topicId);
            model.addAttribute("pagingObject", invisibleThreadPage.getPaging());
            model.addAttribute("threads", invisibleThreadPage.getThreads());
            model.addAttribute("deletePermission",
                    user.checkPermission(forumId, Permission.DELETE_THREAD));
            return "list-threads-moderator";
        }

        return "error-no-permission";
    }

    /**
     * Approving thread by moderator.
     *
     * @param forumIdLong  the forum id
     * @param topicIdLong  the topic id
     * @param threadIdLong the thread that should be approved
     * @param token        token from Keycloak
     * @return The template for the threads
     */
    @PostMapping("approveThread")
    public String approveThread(@RequestParam("forumId") Long forumIdLong,
                                @RequestParam("topicId") Long topicIdLong,
                                @RequestParam("threadId") Long threadIdLong,
                                KeycloakAuthenticationToken token) {

        ForumId forumIdWrapped = new ForumId(forumIdLong);
        User user = this.userService.getUserFromDB(token);

        if (user.checkPermission(forumIdWrapped, Permission.MODERATE_THREAD)) {
            ThreadId threadId = new ThreadId(threadIdLong);
            this.threadService.setThreadVisible(threadId);
            return "redirect:/foren/topic/" + forumIdLong + "/" + topicIdLong + "?page=1";
        }

        return "error-no-permission";
    }

    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenficationToken
     * @return They keycloak account
     */
    @ModelAttribute("account")
    public Account addAccountToTheRequest(KeycloakAuthenticationToken token) {
        if (token == null) {
            return null;
        }

        return this.keycloakService.createAccountFromPrincipal(token);
    }

}
