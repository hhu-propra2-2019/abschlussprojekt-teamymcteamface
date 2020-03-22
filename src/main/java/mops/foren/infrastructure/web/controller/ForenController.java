package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.*;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.web.ForumForm;
import mops.foren.infrastructure.web.PostForm;
import mops.foren.infrastructure.web.ThreadForm;
import mops.foren.infrastructure.web.TopicForm;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@SessionScope
@RequestMapping("/foren/my-forums")
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
     * Method creats a new Forum.
     *
     * @param token     Keyclock token.
     * @param forumForm A model attribute from thymleaf.
     * @param errors    Errors that occured during the parsing of the ForumForm.
     * @return A redirect to my-forums.
     */
    @PostMapping("/newForum")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String newForum(KeycloakAuthenticationToken token,
                           @ModelAttribute @Valid ForumForm forumForm,
                           Errors errors) {

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
    @GetMapping("/{forenID}")
    public String enterAForum(@PathVariable String forenID,
                              Model model) {

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
    @GetMapping("/{forenID}/{topicID}")
    public String enterATopic(@PathVariable String forenID,
                              @PathVariable String topicID,
                              Model model) {

        ForumId forumId = new ForumId(Long.valueOf(forenID));
        model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
        model.addAttribute("forumId", forenID);
        model.addAttribute("topicId", topicID);

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
    public String displayAThread(@RequestParam("threadId") Long threadID,
                                 @RequestParam("page") Integer page,
                                 Model model) {
        ThreadId threadId = new ThreadId(threadID);
        PostPage postPage = this.postService.getPosts(threadId, page - 1);
        model.addAttribute("thread", this.threadService.getThread(threadId));
        model.addAttribute("posts", postPage.getPosts());
        model.addAttribute("pagingObject", postPage.getPaging());
        model.addAttribute("form", this.postForm);
        return "thread";
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
        model.addAttribute("form", this.threadForm);
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
    @PostMapping("/forum/newThread")
    public String addNewThread(KeycloakAuthenticationToken token,
                               @RequestParam("forenId") Long forenIdLong,
                               @RequestParam("topicId") Long topicIdLong,
                               @ModelAttribute ThreadForm threadForm) {
        User user = this.userService.getUserFromDB(token);
        TopicId topicId = new TopicId(topicIdLong);
        Thread thread = threadForm.getThread(user, topicId);
        this.topicService.addThreadInTopic(topicId, thread);
        return "redirect:/my-forums/" + forenIdLong + "/" + topicIdLong;
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
        model.addAttribute("form", new TopicForm("", "", false));
        model.addAttribute("forenId", forenID);
        return "create-topic";
    }

    /**
     * Creating a topic and redirecting to the forum page.
     *
     * @param topicForm   a form to create topics.
     * @param forumIdLong The id of the thread the post is in.
     * @return The template for the thread
     */
    @PostMapping("/forum/newTopic")
    public String newTopic(@ModelAttribute TopicForm topicForm,
                           @RequestParam("forenId") Long forumIdLong) {
        ForumId forumId = new ForumId(forumIdLong);
        Topic topic = topicForm.getTopic(forumId);
        this.forumService.addTopicInForum(forumId, topic);
        return "redirect:/my-forums/" + forumIdLong;
    }
}
