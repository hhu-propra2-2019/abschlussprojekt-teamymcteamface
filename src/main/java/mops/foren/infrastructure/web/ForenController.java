package mops.foren.infrastructure.web;

import mops.foren.applicationservices.*;
import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.PostPage;
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
public class ForenController {

    private UserService userService;
    private ForumService forumService;
    private TopicService topicService;
    private ThreadService threadService;
    private PostService postService;
    private ForumForm form = new ForumForm("", "");

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
     * @param forenID  the forum id
     * @param topicID  the topic id
     * @param threadID the thread id
     * @param model    the model
     * @return The template for the thread
     */
    @GetMapping("/my-forums/{forenID}/{topicID}/{threadID}")
    public String displayAThread(@PathVariable String forenID, @PathVariable String topicID,
                                 @PathVariable String threadID, Model model,
                                 @RequestParam Integer page) {

        ThreadId threadId = new ThreadId(Long.valueOf(threadID));
        model.addAttribute("threadTitle", this.threadService.getThread(threadId));

        PostPage postPage = this.postService.getPosts(threadId, page-1);
        model.addAttribute("posts", postPage.getPosts());
        model.addAttribute("pagingObject", postPage.getPaging());

        return "thread";
    }

    @GetMapping("/my-forums/{forenID}/{topicID}/new-thread")
    public String createNewThread(@PathVariable String forenID, @PathVariable String topicID) {
        return "create-thread";
    }
}
