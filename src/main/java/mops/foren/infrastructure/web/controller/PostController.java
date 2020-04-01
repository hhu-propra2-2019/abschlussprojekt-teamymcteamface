package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import mops.foren.infrastructure.web.PostForm;
import mops.foren.infrastructure.web.ValidationService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


@Controller
@SessionScope
@RequestMapping("/foren/post")
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class PostController {

    private UserService userService;
    private ThreadService threadService;
    private KeycloakService keycloakService;
    private ValidationService validationService;
    private PostService postService;


    /**
     * Constructor for ForenController. The parameters that are injected.
     *
     * @param userService       - injected UserService (ApplicationService)
     * @param threadService     - ThreadService (ApplicationService)
     * @param keycloakService   - KeycloakService (Infrastructure Service)
     * @param postService       - PostService (PostService)
     * @param validationService - ValidationService (Infrastructure Service)
     */
    public PostController(UserService userService,
                          ThreadService threadService,
                          PostService postService,
                          KeycloakService keycloakService,
                          ValidationService validationService) {
        this.userService = userService;
        this.threadService = threadService;
        this.postService = postService;
        this.keycloakService = keycloakService;
        this.validationService = validationService;
    }

    /**
     * Creating a post and redirecting to the thread page.
     *
     * @param token        Keycloak token
     * @param postForm     a form to create posts.
     * @param threadIdLong The id of the thread the post is in.
     * @return The template for the thread.
     */
    @PostMapping("/new-post")
    public String newPost(KeycloakAuthenticationToken token,
                          @RequestParam("threadId") Long threadIdLong,
                          @RequestParam("page") Integer page,
                          @Valid @ModelAttribute PostForm postForm,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String errorMessage =
                    this.validationService.getErrorDescriptionFromErrorObjects(bindingResult);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return String.format("redirect:/foren/thread?threadId=%d&page=%d",
                    threadIdLong, page + 1);
        }
        redirectAttributes.addFlashAttribute("error", null);

        User user = this.userService.getUserFromDB(token);
        ThreadId threadId = new ThreadId(threadIdLong);
        Post post = postForm.getPost(user, threadId);
        Thread threadById = this.threadService.getThreadById(threadId);
        ForumId forumId = threadById.getForumId();

        if (user.checkPermission(forumId, Permission.CREATE_POST)) {
            this.threadService.addPostInThread(threadId, post);
            if (threadById.getModerated()) {
                return "notification-addPost-moderation";
            }
            return String.format("redirect:/foren/thread?threadId=%d&page=%d",
                    threadIdLong, page + 1);
        }
        return "error-no-permission";
    }

    /**
     * Approve a post.
     *
     * @param postIdLong The post id
     * @return Redirect to the thread page.
     */
    @PostMapping("/approve-post")
    public String approvePost(KeycloakAuthenticationToken token,
                              @RequestParam("postId") Long postIdLong,
                              @RequestParam("page") Integer page) {

        User user = this.userService.getUserFromDB(token);
        PostId postId = new PostId(postIdLong);
        ThreadId threadId = this.postService.getPost(postId).getThreadId();
        ForumId forumId = this.postService.getPost(postId).getForumId();

        if (user.checkPermission(forumId, Permission.MODERATE_THREAD)) {
            this.postService.setPostVisible(postId);
            return String.format("redirect:/foren/thread?threadId=%d&page=%d",
                    threadId.getId(), page + 1);
        }
        return "error-no-permission";
    }

    /**
     * Delete a post.
     *
     * @param token      The key cloak token
     * @param postIdLong The post id
     * @param page       The current page
     * @return The thread page or the error.
     */
    @PostMapping("/delete-post")
    public String deletePost(KeycloakAuthenticationToken token,
                             @RequestParam("postId") Long postIdLong,
                             @RequestParam("page") Integer page) {

        User user = this.userService.getUserFromDB(token);
        Post post = this.postService.getPost(new PostId(postIdLong));
        ForumId forumId = post.getForumId();
        ThreadId threadId = post.getThreadId();

        if (user.checkPermission(forumId, Permission.DELETE_POST, post.getAuthor())) {
            this.postService.deletePost(post.getId());
            return String.format("redirect:/foren/thread?threadId=%d&page=%d",
                    threadId.getId(), page + 1);
        }
        return "error-no-permission";
    }


    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenticationToken
     * @return The keycloak account
     */
    @ModelAttribute("account")
    public Account addAccountToTheRequest(KeycloakAuthenticationToken token) {
        if (token == null) {
            return null;
        }

        return this.keycloakService.createAccountFromPrincipal(token);
    }
}
