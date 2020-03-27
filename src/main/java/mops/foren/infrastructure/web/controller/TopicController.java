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
     * Constructor for TopicController. The parameters that are injected.
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
     * @param token       They keycloak token
     * @param topicIdLong the topic id
     * @param model       the model
     * @return The template for the threads.
     */
    @GetMapping
    public String enterATopic(KeycloakAuthenticationToken token,
                              @RequestParam("topicId") Long topicIdLong,
                              @RequestParam("page") Integer page,
                              Model model) {
        User user = this.userService.getUserFromDB(token);
        ForumId forumId = this.topicService.getTopic(new TopicId(topicIdLong)).getForumId();
        TopicId topicId = new TopicId(topicIdLong);
        ThreadPage visibleThreadPage =
                this.threadService.getThreadPageByVisibility(topicId, page - 1, true);
        Integer countInvisibleThreads = this.threadService.countInvisibleThreads(topicId);
        if (user.checkPermission(forumId, Permission.READ_TOPIC)) {
            model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
            model.addAttribute("topic", this.topicService.getTopic(topicId));
            model.addAttribute("forumId", forumId.getId());
            model.addAttribute("topicId", topicId.getId());
            model.addAttribute("pagingObject", visibleThreadPage.getPaging());
            model.addAttribute("threads", visibleThreadPage.getThreads());
            model.addAttribute("countInvisibleThreads", countInvisibleThreads);
            model.addAttribute("moderatePermission",
                    user.checkPermission(forumId, Permission.MODERATE_THREAD));
            model.addAttribute("deletePermission",
                    user.checkPermission(forumId, Permission.DELETE_THREAD));
            return "list-threads";
        }
        return "error-no-permission";
    }


    /**
     * Mapping to the topic page for moderation.
     *
     * @param topicIdLong the topic id
     * @param page        The thread page
     * @param model       the model
     * @return The template for the threads
     */
    @GetMapping("/moderationview")
    public String enterATopicAsModerator(KeycloakAuthenticationToken token,
                                         @RequestParam("topicId") Long topicIdLong,
                                         @RequestParam("page") Integer page,
                                         Model model) {

        TopicId topicId = new TopicId(topicIdLong);
        ForumId forumId = this.topicService.getTopic(topicId).getForumId();
        User user = this.userService.getUserFromDB(token);

        if (user.checkPermission(forumId, Permission.MODERATE_THREAD)) {
            ThreadPage invisibleThreadPage =
                    this.threadService.getThreadPageByVisibility(topicId, page - 1, false);
            model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
            model.addAttribute("forumId", forumId.getId());
            model.addAttribute("topicId", topicId.getId());
            model.addAttribute("pagingObject", invisibleThreadPage.getPaging());
            model.addAttribute("threads", invisibleThreadPage.getThreads());
            model.addAttribute("deletePermission",
                    user.checkPermission(forumId, Permission.DELETE_THREAD));
            return "list-threads-moderator";
        }
        return "error-no-permission";
    }


    /**
     * Create a new topic.
     *
     * @param forumIdLong The forum id
     * @param model       The model
     * @return The template for creating a new topic.
     */
    @GetMapping("/create-topic")
    public String createNewTopic(@RequestParam("forumId") Long forumIdLong,
                                 Model model) {
        model.addAttribute("error", this.errorMessage);
        model.addAttribute("form", new TopicForm("", "", false, false));
        model.addAttribute("forumId", forumIdLong);
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
     * @return The template for the thread.
     */
    @PostMapping("/add-topic")
    public String newTopic(KeycloakAuthenticationToken token,
                           @RequestParam("forumId") Long forumIdLong,
                           @Valid @ModelAttribute TopicForm topicForm,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            this.errorMessage =
                    this.validationService.getErrorDescriptionFromErrorObjects(bindingResult);

            return String.format("redirect:/foren/topic/create-topic?forumId=%d", forumIdLong);
        }
        this.errorMessage = null;

        User user = this.userService.getUserFromDB(token);
        ForumId forumId = new ForumId(forumIdLong);

        if (user.checkPermission(forumId, Permission.CREATE_TOPIC)) {
            Topic topic = topicForm.getTopic(forumId);
            this.forumService.addTopicInForum(forumId, topic);
            return String.format("redirect:/foren/my-forums/enter?forumId=%d", forumIdLong);
        }
        return "error-no-permission";
    }

    /**
     * Delete a topic.
     *
     * @param token       The keycloak token
     * @param topicIdLong The topic Id
     * @return Redirect to forum mainpage or to error page
     */
    @PostMapping("/delete-topic")
    public String deleteTopic(KeycloakAuthenticationToken token,
                              @RequestParam("topicId") Long topicIdLong) {
        User user = this.userService.getUserFromDB(token);
        TopicId topicId = new TopicId(topicIdLong);
        ForumId forumId = this.topicService.getTopic(topicId).getForumId();

        if (user.checkPermission(forumId, Permission.DELETE_TOPIC)) {
            this.topicService.deleteTopic(topicId);
            return String.format("redirect:/foren/my-forums/enter?forumId=%d", forumId.getId());
        }
        return "error-no-permission";
    }


    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenticationToken
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
