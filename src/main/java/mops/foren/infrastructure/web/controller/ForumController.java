package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.*;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.web.ForumForm;
import mops.foren.infrastructure.web.PostForm;
import mops.foren.infrastructure.web.ThreadForm;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;

@Controller
@SessionScope
@RequestMapping("/foren/my-forums")
public class ForumController {

    private UserService userService;
    private ForumService forumService;
    private TopicService topicService;
    private ThreadService threadService;
    private PostService postService;
    private ForumForm form = new ForumForm("", "");
    private PostForm postForm = new PostForm("");
    private ThreadForm threadForm = new ThreadForm("", "");

    /**
     * Constructor for ForenController. The parameters are injected.
     *
     * @param userService   - injected UserService (ApplicationService)
     * @param forumService  - injected ForumService (ApplicationService)
     * @param topicService  - TopicService (ApplicationService)
     * @param threadService - ThreadService (ApplicationService)
     */
    public ForumController(UserService userService, ForumService forumService,
                           TopicService topicService, ThreadService threadService,
                           PostService postService) {
        this.userService = userService;
        this.forumService = forumService;
        this.topicService = topicService;
        this.threadService = threadService;
        this.postService = postService;
    }


    /**
     * Method checks if user is in the DB and gets all his forums.
     *
     * @param token Keycloak token.
     * @param model Content we add to html per thymleaf.
     * @return Get-mapping for my-forums.
     */
    @GetMapping
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String allForum(KeycloakAuthenticationToken token,
                           Model model) {
        User user = this.userService.getUserFromDB(token);
        model.addAttribute("forums", this.forumService.getForums(user));
        model.addAttribute("forum", this.form);
        return "my-forums";
    }

    /**
     * Mapping to the main-page of a certain forum.
     *
     * @param forenID - id of the forum
     * @param model   - Model
     * @return The template for the forum main page
     */
    @GetMapping("/{forenID}")
    public String enterAForum(@PathVariable String forenID,
                              Model model) {

        ForumId forumIdWrapped = new ForumId(Long.valueOf(forenID));

        model.addAttribute("topics", this.topicService.getTopics(forumIdWrapped));
        model.addAttribute("forum", this.forumService.getForum(forumIdWrapped));

        return "forum-mainpage";
    }

    /**
     * Creating a post and redirecting to the thread page.
     *
     * @param token        Keycloak token
     * @param postForm     a form to create posts.
     * @param threadIdLong The id of the thread the post is in.
     * @return The template for the thread
     */
    @PostMapping("/post/newPost")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String newPost(KeycloakAuthenticationToken token,
                          @ModelAttribute PostForm postForm,
                          @RequestParam("threadId") Long threadIdLong,
                          @RequestParam("page") Integer page) {
        ThreadId threadId = new ThreadId(threadIdLong);
        User user = this.userService.getUserFromDB(token);
        Post post = postForm.getPost(user, threadId);
        this.threadService.addPostInThread(threadId, post);
        return "redirect:/thread?threadId=" + threadIdLong + "&page=" + (page + 1);
    }

}
