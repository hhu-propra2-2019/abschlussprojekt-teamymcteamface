package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.TopicService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import mops.foren.infrastructure.web.PostForm;
import mops.foren.infrastructure.web.ThreadForm;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

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

    /**
     * Constructor for ThreadController. The parameters are injected.
     *
     * @param threadService   - ThreadService (ApplicationService)
     * @param postService     - injected PostService (ApplicationService)
     * @param userService     - injected UserService (ApplicationService)
     * @param topicService    - TopicService (ApplicationService)
     * @param keycloakService - KeycloakService (Infrastructure Service)
     */
    public ThreadController(ThreadService threadService, PostService postService,
                            UserService userService, TopicService topicService,
                            KeycloakService keycloakService) {
        this.threadService = threadService;
        this.postService = postService;
        this.userService = userService;
        this.topicService = topicService;
        this.keycloakService = keycloakService;
    }

    /**
     * Mapping to the thread page.
     *
     * @param threadID the thread id
     * @param model    the model
     * @return The template for the thread
     */
    @GetMapping
    public String displayAThread(@RequestParam("threadId") Long threadID,
                                 @RequestParam("page") Integer page,
                                 Model model) {
        ThreadId threadId = new ThreadId(threadID);
        PostPage postPage = this.postService.getPosts(threadId, page - 1);
        model.addAttribute("thread", this.threadService.getThreadById(threadId));
        model.addAttribute("posts", postPage.getPosts());
        model.addAttribute("pagingObject", postPage.getPaging());
        model.addAttribute("form", new PostForm(""));
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
        model.addAttribute("form", new ThreadForm("", ""));
        model.addAttribute("forenId", new ForumId(forenID));
        model.addAttribute("topicId", new TopicId(topicID));
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
                               @Valid @ModelAttribute ThreadForm threadForm) {
        User user = this.userService.getUserFromDB(token);
        TopicId topicId = new TopicId(topicIdLong);
        Thread thread = threadForm.getThread(user, topicId);
        this.topicService.addThreadInTopic(topicId, thread);
        return String.format("redirect:/foren/topic/%d/%d/%s", forenIdLong, topicIdLong, "?page=1");
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
