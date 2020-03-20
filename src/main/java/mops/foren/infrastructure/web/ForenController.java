package mops.foren.infrastructure.web;

import mops.foren.applicationservices.*;
import mops.foren.domain.model.*;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@Controller
@SessionScope
public class ForenController {

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
    public ForenController(UserService userService, ForumService forumService,
                           TopicService topicService, ThreadService threadService,
                           PostService postService) {
        this.userService = userService;
        this.forumService = forumService;
        this.topicService = topicService;
        this.threadService = threadService;
        this.postService = postService;
    }

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }


    /**
     * Method checks if user is in the DB and gets all his forums.
     *
     * @param token Keycloak token.
     * @param model Content we add to html per thymleaf.
     * @return Get-mapping for my-forums.
     */
    @GetMapping("/my-forums")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String allForum(KeycloakAuthenticationToken token, Model model) {
        User user = this.userService.getUserFromDB(token);
        model.addAttribute("forums", this.forumService.getForums(user));
        model.addAttribute("forum", this.form);
        return "my-forums";
    }


    /**
     * Method creats a new Forum.
     *
     * @param token     Keyclock token.
     * @param forumForm A model attribute from thymleaf.
     * @param errors    Errors that occured during the parsing of the ForumForm.
     * @return A redirect to my-forums.
     */
    @PostMapping("/my-forums/newForum")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String newForum(KeycloakAuthenticationToken token,
                           @ModelAttribute @Valid ForumForm forumForm, Errors errors) {

        User user = this.userService.getUserFromDB(token);
        Forum forum = forumForm.getForum();
        this.userService.addForumInUser(user, forum);
        return "redirect:/my-forums";
    }

    /**
     * Mapping to the main-page of a certain forum.
     *
     * @param forenID - id of the forum
     * @param model   - Model
     * @return The template for the forum main page
     */
    @GetMapping("/my-forums/{forenID}")
    public String enterAForum(@PathVariable String forenID, Model model) {

        ForumId forumIdWrapped = new ForumId(Long.valueOf(forenID));

        model.addAttribute("topics", this.topicService.getTopics(forumIdWrapped));
        model.addAttribute("forum", this.forumService.getForum(forumIdWrapped));

        return "forum-mainpage";
    }

    /**
     * Mapping to the topic page.
     *
     * @param forenID the forum id
     * @param topicID the topic id
     * @param model   the model
     * @return The template for the threads
     */
    @GetMapping("/my-forums/{forenID}/{topicID}")
    public String enterATopic(@PathVariable String forenID,
                              @PathVariable String topicID, Model model) {

        ForumId forumId = new ForumId(Long.valueOf(forenID));
        model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
        model.addAttribute("forumId", forenID);
        model.addAttribute("topicId", topicID);
        model.addAttribute("author", "nesu57");

        TopicId topicId = new TopicId(Long.valueOf(topicID));
        model.addAttribute("threads", this.threadService.getThreads(topicId));

        return "list-threads";
    }

    /**
     * Mapping to the thread page.
     *
     * @param threadID the thread id
     * @param model    the model
     * @return The template for the thread
     */
    @GetMapping("/thread")
    public String displayAThread(@RequestParam("threadId") Long threadID, Model model) {
        ThreadId threadId = new ThreadId(threadID);
        List<Post> posts = this.postService.getPosts(threadId);
        model.addAttribute("threadTitle", this.threadService.getThread(threadId));
        model.addAttribute("posts", posts);
        model.addAttribute("post", this.postForm);

        return "thread2";
    }

    /**
     * Creating a post and redirecting to the thread page.
     *
     * @param token     Keyclock token
     * @param postForm  a form to create posts.
     * @param threadIdL The id of the thread the post is in.
     * @return The template for the thread
     */
    @PostMapping("/post/newPost")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String newPost(KeycloakAuthenticationToken token, @ModelAttribute PostForm postForm,
                          @RequestParam("threadId") Long threadIdL) {
        ThreadId threadId = new ThreadId(threadIdL);
        User user = this.userService.getUserFromDB(token);
        Post post = postForm.getPost(user, threadId);
        this.threadService.addPostInThread(threadId, post);
        return "redirect:/thread?threadId=" + threadIdL;
    }

    @GetMapping("/my-forums/{forenID}/{topicID}/new-thread")
    public String createNewThread(@PathVariable Long forenID, @PathVariable Long topicID, Model model) {
        model.addAttribute("form", this.threadForm);
        System.out.println(topicID);
        System.out.println(forenID);
        model.addAttribute("forenId", new ForumId(forenID));
        model.addAttribute("topicId", new TopicId(topicID));
        return "create-thread";
    }

    @PostMapping("/forum/newThread")
    public String addNewThread(@RequestParam("forenId") Long forenId, @RequestParam("topicId") Long topicId,
                               @ModelAttribute ThreadForm threadForm) {
        System.out.println(threadForm);
        return "redirect:/my-forums/" + forenId + "/" + topicId;
    }
}
