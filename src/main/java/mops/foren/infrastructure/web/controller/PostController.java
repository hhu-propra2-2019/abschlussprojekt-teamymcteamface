package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import mops.foren.infrastructure.web.PostForm;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

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
    private PostService postService;


    /**
     * Constructor for ForenController. The parameters are injected.
     *
     * @param userService     - injected UserService (ApplicationService)
     * @param threadService   - ThreadService (ApplicationService)
     * @param keycloakService - KeycloakService (Infrastructure Service)
     * @param postService     - PostService (PostService)
     */
    public PostController(UserService userService, ThreadService threadService,
                          KeycloakService keycloakService, PostService postService) {
        this.userService = userService;
        this.threadService = threadService;
        this.keycloakService = keycloakService;
        this.postService = postService;
    }

    /**
     * Creating a post and redirecting to the thread page.
     *
     * @param token        Keycloak token
     * @param postForm     a form to create posts.
     * @param threadIdLong The id of the thread the post is in.
     * @return The template for the thread
     */
    @PostMapping("/new-post")
    public String newPost(KeycloakAuthenticationToken token,
                          @Valid @ModelAttribute PostForm postForm,
                          @RequestParam("threadId") Long threadIdLong,
                          @RequestParam("page") Integer page) {
        ThreadId threadId = new ThreadId(threadIdLong);
        User user = this.userService.getUserFromDB(token);
        Post post = postForm.getPost(user, threadId);
        this.threadService.addPostInThread(threadId, post);
        return String.format("redirect:/foren/thread?threadId=%d&page=%d", threadIdLong, page + 1);
    }

    @PostMapping("/approvePost")
    public String approvePost(@RequestParam("postId") Long postIdLong,
                              @RequestParam("threadId") Long threadIdLong) {
        this.postService.setPostVisible(new PostId(postIdLong));
        return String.format("redirect:/foren/thread?threadId=%d&page=1", threadIdLong);
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
