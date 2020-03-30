package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.TopicService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.web.*;
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
@RequestMapping("/foren/thread")
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class ThreadController {
    private ThreadService threadService;
    private PostService postService;
    private UserService userService;
    private TopicService topicService;
    private KeycloakService keycloakService;
    private ValidationService validationService;
    private String threadErrorMessage;

    /**
     * Constructor for ThreadController. The parameters that are injected.
     *
     * @param threadService     - ThreadService (ApplicationService)
     * @param postService       - injected PostService (ApplicationService)
     * @param userService       - injected UserService (ApplicationService)
     * @param topicService      - TopicService (ApplicationService)
     * @param keycloakService   - KeycloakService (Infrastructure Service)
     * @param validationService - ValidationService (Infrastructure Service)
     */
    public ThreadController(ThreadService threadService, PostService postService,
                            UserService userService, TopicService topicService,
                            KeycloakService keycloakService,
                            ValidationService validationService) {
        this.threadService = threadService;
        this.postService = postService;
        this.userService = userService;
        this.topicService = topicService;
        this.keycloakService = keycloakService;
        this.validationService = validationService;
    }

    /**
     * Mapping to the thread page.
     *
     * @param threadID the thread id
     * @param model    the model
     * @return The template for the thread.
     */
    @GetMapping
    public String displayAThread(KeycloakAuthenticationToken token,
                                 @RequestParam("threadId") Long threadID,
                                 @RequestParam("page") Integer page,
                                 @ModelAttribute("error") String postErrorMessage,
                                 Model model) {

        User user = this.userService.getUserFromDB(token);
        ThreadId threadId = new ThreadId(threadID);
        PostPage postPage = this.postService.getPosts(threadId, page - 1);
        Thread threadById = this.threadService.getThreadById(threadId);
        model.addAttribute("error", postErrorMessage);
        model.addAttribute("thread", threadById);
        model.addAttribute("posts", postPage.getPosts());
        model.addAttribute("pagingObject", postPage.getPaging());
        model.addAttribute("form", new PostForm(""));
        model.addAttribute("user", user);
        model.addAttribute("PermissionDelete", Permission.DELETE_POST);
        model.addAttribute("moderator",
                user.checkPermission(threadById.getForumId(), Permission.MODERATE_THREAD));
        model.addAttribute("minContentLength", MIN_CONTENT_LENGTH);
        model.addAttribute("maxContentLength", MAX_CONTENT_LENGTH);
        return "thread";
    }

    /**
     * Brings up the form to create a new thread.
     *
     * @param topicIdLong The topic id
     * @param model       The model
     * @return The template to create a new thread.
     */
    @GetMapping("/new-thread")
    public String createNewThread(@RequestParam("topicId") Long topicIdLong,
                                  Model model) {

        model.addAttribute("error", this.threadErrorMessage);
        model.addAttribute("form", new ThreadForm("", ""));
        model.addAttribute("topicId", topicIdLong);
        model.addAttribute("minTitleLength", MIN_TITLE_LENGTH);
        model.addAttribute("maxTitleLength", MAX_TITLE_LENGTH);
        model.addAttribute("minContentLength", MIN_CONTENT_LENGTH);
        model.addAttribute("maxContentLength", MAX_CONTENT_LENGTH);

        return "create-thread";
    }

    /**
     * Creates a thread and redirects to the topic page.
     *
     * @param token       The user token
     * @param topicIdLong The topic id
     * @param threadForm  The form that that includes the new thread
     * @return The topic page template.
     */
    @PostMapping("/add-thread")
    public String addNewThread(KeycloakAuthenticationToken token,
                               @RequestParam("topicId") Long topicIdLong,
                               @Valid @ModelAttribute ThreadForm threadForm,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            this.threadErrorMessage =
                    this.validationService.getErrorDescriptionFromErrorObjects(bindingResult);
            return String.format("redirect:/foren/thread/new-thread?topicId=%d", topicIdLong);
        }
        this.threadErrorMessage = null;

        User user = this.userService.getUserFromDB(token);
        TopicId topicId = new TopicId(topicIdLong);
        ForumId forumId = this.topicService.getTopic(topicId).getForumId();
        Thread thread = threadForm.getThread(user, topicId);

        if (user.checkPermission(forumId, Permission.CREATE_THREAD)) {
            this.topicService.addThreadInTopic(topicId, thread);
            return String.format("redirect:/foren/topic/?topicId=%d&page=1", topicIdLong);
        }
        return "error-no-permission";
    }

    /**
     * Approve a thread by a moderator.
     *
     * @param threadIdLong the thread that should be approved
     * @param token        token from Keycloak
     * @return The template for the threads.
     */
    @PostMapping("/approve-thread")
    public String approveThread(KeycloakAuthenticationToken token,
                                @RequestParam("threadId") Long threadIdLong) {

        User user = this.userService.getUserFromDB(token);
        Thread thread = this.threadService.getThreadById(new ThreadId(threadIdLong));
        ForumId forumId = thread.getForumId();

        if (user.checkPermission(forumId, Permission.MODERATE_THREAD)) {
            this.threadService.setThreadVisible(new ThreadId(threadIdLong));
            return String.format("redirect:/foren/thread?threadId=%d&page=1", threadIdLong);
        }
        return "error-no-permission";
    }

    /**
     * Delete a thread.
     *
     * @param token        The keycloak token
     * @param threadIdLong The thread id
     * @return Redirect to list-thread or to error page
     */
    @PostMapping("/delete-thread")
    public String deleteThread(KeycloakAuthenticationToken token,
                               @RequestParam("threadId") Long threadIdLong) {

        User user = this.userService.getUserFromDB(token);
        ThreadId threadId = new ThreadId(threadIdLong);
        Thread thread = this.threadService.getThreadById(threadId);
        ForumId forumId = thread.getForumId();
        TopicId topicId = thread.getTopicId();

        if (user.checkPermission(forumId, Permission.DELETE_THREAD, thread.getAuthor())) {
            this.threadService.deleteThread(threadId);
            return String.format("redirect:/foren/topic?topicId=%d&page=1", topicId.getId());
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
