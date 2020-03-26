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
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

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

    /**
     * Constructor for TopicController. The parameters are injected.
     *
     * @param forumService    - injected ForumService (ApplicationService)
     * @param threadService   - ThreadService (ApplicationService)
     * @param userService     - UserService (ApplicationService)
     * @param keycloakService - KeycloakService (Infrastructure Service)
     */
    public TopicController(ForumService forumService, TopicService topicService,
                           ThreadService threadService, UserService userService,
                           KeycloakService keycloakService) {
        this.forumService = forumService;
        this.topicService = topicService;
        this.threadService = threadService;
        this.userService = userService;
        this.keycloakService = keycloakService;
    }

    /**
     * Mapping to the topic page.
     *
     * @param token       They keycloak token
     * @param topicIdLong the topic id
     * @param model       the model
     * @return The template for the threads
     */
    @GetMapping
    public String enterATopic(KeycloakAuthenticationToken token,
                              @RequestParam("topicId") Long topicIdLong,
                              @RequestParam("page") Integer page,
                              Model model) {
        User user = this.userService.getUserFromDB(token);
        ForumId forumId = topicService.getTopic(new TopicId(topicIdLong)).getForumId();
        TopicId topicId = new TopicId(topicIdLong);
        ThreadPage visibleThreadPage =
                this.threadService.getThreadPageByVisibility(topicId, page - 1, true);
        Integer countInvisibleThreads = this.threadService.countInvisibleThreads(topicId);
        if (user.checkPermission(forumId, Permission.READ_TOPIC)) {
            model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
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
     * Create a new topic.
     *
     * @param forumIdLong The forum id
     * @param model       The model
     * @return The template for creating a new topic
     */
    @GetMapping("/create-topic")
    public String createNewTopic(@RequestParam("forumId") Long forumIdLong,
                                 Model model) {
        model.addAttribute("form", new TopicForm("", "", false, false));
        model.addAttribute("forumId", forumIdLong);
        return "create-topic";
    }

    /**
     * Creating a topic and redirecting to the forum page.
     *
     * @param topicForm   a form to create topics.
     * @param forumIdLong The id of the thread the post is in.
     * @return The template for the thread
     */
    @PostMapping("/add-topic")
    public String newTopic(@Valid @ModelAttribute TopicForm topicForm,
                           @RequestParam("forumId") Long forumIdLong) {
        ForumId forumId = new ForumId(forumIdLong);
        Topic topic = topicForm.getTopic(forumId);
        this.forumService.addTopicInForum(forumId, topic);
        return String.format("redirect:/foren/my-forums/enter?forumId=%d", forumIdLong);
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
                              @RequestParam("forumId") Long forumIdLong,
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
    public String enterATopicAsModerator(@RequestParam("topicId") Long topicIdLong,
                                         @RequestParam("page") Integer page,
                                         Model model,
                                         KeycloakAuthenticationToken token) {

        ForumId forumId = topicService.getTopic(new TopicId(topicIdLong)).getForumId();
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
            return String.format("redirect:/foren/topic/?forumId=%d&topicId=%d&page=1", forumIdLong, topicIdLong);
        }

        return "error-no-permission";
    }

    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenticationToken
     * @return Keycloak Account
     */
    @ModelAttribute("account")
    public Account addAccountToTheRequest(KeycloakAuthenticationToken token) {
        if (token == null) {
            return null;
        }

        return this.keycloakService.createAccountFromPrincipal(token);
    }

}
