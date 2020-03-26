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
     * Constructor for ThreadController. The parameters are injected.
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
     * @return The template for the thread
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
        model.addAttribute("thread", this.threadService.getThreadById(threadId));
        model.addAttribute("thread", threadById);
        model.addAttribute("posts", postPage.getPosts());
        model.addAttribute("pagingObject", postPage.getPaging());
        model.addAttribute("form", new PostForm(""));
        model.addAttribute("user", user);
        model.addAttribute("moderator",
                user.checkPermission(threadById.getForumId(), Permission.MODERATE_THREAD));
        model.addAttribute("minContentLength", MIN_CONTENT_LENGTH);
        model.addAttribute("maxContentLength", MAX_CONTENT_LENGTH);
        return "thread";
    }

    /**
     * Brings up the form to create a new thread.
     *
     * @param forenID The forum id
     * @param topicID The topic id
     * @param model   The model
     * @return The template to create a new thread
     */
    @GetMapping("/{forenID}/{topicID}/new-thread")
    public String createNewThread(@PathVariable Long forenID,
                                  @PathVariable Long topicID,
                                  Model model) {
        model.addAttribute("error", this.threadErrorMessage);
        model.addAttribute("form", new ThreadForm("", ""));
        model.addAttribute("forenId", new ForumId(forenID));
        model.addAttribute("topicId", new TopicId(topicID));
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
     * @param forenIdLong The forum id
     * @param topicIdLong The topic id
     * @param threadForm  The form that that includes the new thread
     * @return The topic page template
     */
    @PostMapping("/new-thread")
    public String addNewThread(KeycloakAuthenticationToken token,
                               @RequestParam("forenId") Long forenIdLong,
                               @RequestParam("topicId") Long topicIdLong,
                               @Valid @ModelAttribute ThreadForm threadForm,
                               BindingResult bindingResult) {

        // for failed validation
        if (bindingResult.hasErrors()) {

            this.threadErrorMessage =
                    this.validationService.getErrorDescriptionFromErrorObjects(bindingResult);

            return String.format("redirect:/foren/thread/%d/%d/new-thread",
                    forenIdLong,
                    topicIdLong);
        }

        // Set to null after successful validation
        this.threadErrorMessage = null;

        User user = this.userService.getUserFromDB(token);
        TopicId topicId = new TopicId(topicIdLong);
        Thread thread = threadForm.getThread(user, topicId);
        this.topicService.addThreadInTopic(topicId, thread);
        return String.format("redirect:/foren/topic/%d/%d/%s", forenIdLong, topicIdLong, "?page=1");
    }

    /**
     * Delete a thread.
     *
     * @param token        The keycloak token
     * @param forenIdLong  The forum id
     * @param topicIdLong  The topic id
     * @param threadIdLong The thread id
     * @return Redirect to list-thread or to error page
     */
    @PostMapping("/delete-thread")
    public String deleteThread(KeycloakAuthenticationToken token,
                               @RequestParam("forenId") Long forenIdLong,
                               @RequestParam("topicId") Long topicIdLong,
                               @RequestParam("threadId") Long threadIdLong) {
        User user = this.userService.getUserFromDB(token);
        ForumId forumId = new ForumId(forenIdLong);
        ThreadId threadId = new ThreadId(threadIdLong);
        Thread thread = this.threadService.getThreadById(threadId);

        if (user.checkPermission(forumId, Permission.DELETE_THREAD, thread.getAuthor())) {
            this.threadService.deleteThread(threadId);
            return String.format("redirect:/foren/topic/%d/%d/%s",
                    forenIdLong, topicIdLong, "?page=1");
        }

        return "error-no-permission";
    }


    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenficationToken
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
