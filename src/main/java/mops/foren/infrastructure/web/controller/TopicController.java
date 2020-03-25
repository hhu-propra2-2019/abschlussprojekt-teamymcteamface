package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;
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
    public TopicController(ForumService forumService, ThreadService threadService,
                           UserService userService, KeycloakService keycloakService) {
        this.forumService = forumService;
        this.threadService = threadService;
        this.userService = userService;
        this.keycloakService = keycloakService;
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
                              @RequestParam("page") Integer page,
                              Model model) {

        ForumId forumId = new ForumId(Long.valueOf(forenID));
        TopicId topicId = new TopicId(Long.valueOf(topicID));
        ThreadPage visibleThreadPage = this.threadService.getThreadPageByVisibility(topicId, page - 1, true);
        int unvisibleCount = this.threadService.countUnvisableThreads(topicId);
        model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
        model.addAttribute("forumId", forenID);
        model.addAttribute("topicId", topicID);
        model.addAttribute("pagingObject", visibleThreadPage.getPaging());
        model.addAttribute("threads", visibleThreadPage.getThreads());
        model.addAttribute("unvisibleCount", unvisibleCount);
        return "list-threads";
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
        model.addAttribute("form", new TopicForm("", "", false, false));
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
    @PostMapping("/new-topic")
    public String newTopic(@Valid @ModelAttribute TopicForm topicForm,
                           @RequestParam("forenId") Long forumIdLong) {
        ForumId forumId = new ForumId(forumIdLong);
        Topic topic = topicForm.getTopic(forumId);
        this.forumService.addTopicInForum(forumId, topic);
        return String.format("redirect:/foren/my-forums/%d", forumIdLong);
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

    @GetMapping("/{forenID}/{topicID}/moderationview")
    public String enterATopicAsModerator(@PathVariable String forenID,
                                         @PathVariable String topicID,
                                         @RequestParam("page") Integer page,
                                         Model model) {
        ForumId forumId = new ForumId(Long.valueOf(forenID));
        TopicId topicId = new TopicId(Long.valueOf(topicID));
        ThreadPage unvisibleThreadPage = this.threadService.getThreadPageByVisibility(topicId, page - 1, false);
        model.addAttribute("forumTitle", this.forumService.getForum(forumId).getTitle());
        model.addAttribute("forumId", forenID);
        model.addAttribute("topicId", topicID);
        model.addAttribute("pagingObject", unvisibleThreadPage.getPaging());
        model.addAttribute("threads", unvisibleThreadPage.getThreads());
        return "list-threads-moderator";
    }

    @PostMapping("approveThread")
    public String approveThread(@RequestParam("forenId") Long forumIdLong,
                                @RequestParam("topicId") Long topicIdLong,
                                @RequestParam("threadId") Long threadIdLong) {
        this.threadService.setThreadVisable(threadIdLong);
        return "redirect:/foren/topic/" + forumIdLong + "/" + topicIdLong + "?page=1";
    }

}
