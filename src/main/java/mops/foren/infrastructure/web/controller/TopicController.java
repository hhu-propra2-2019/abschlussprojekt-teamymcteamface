package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.TopicService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;
import mops.foren.infrastructure.web.TopicForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
@RequestMapping("/foren/topic")
public class TopicController {
    private ForumService forumService;
    private TopicService topicService;
    private ThreadService threadService;

    /**
     * Constructor for TopicController. The parameters are injected.
     *
     * @param forumService  - injected ForumService (ApplicationService)
     * @param topicService  - TopicService (ApplicationService)
     * @param threadService - ThreadService (ApplicationService)
     */
    public TopicController(ForumService forumService,
                           TopicService topicService, ThreadService threadService) {
        this.forumService = forumService;
        this.topicService = topicService;
        this.threadService = threadService;
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
    @PostMapping("/new-topic")
    public String newTopic(@ModelAttribute TopicForm topicForm,
                           @RequestParam("forenId") Long forumIdLong) {
        ForumId forumId = new ForumId(forumIdLong);
        Topic topic = topicForm.getTopic(forumId);
        this.forumService.addTopicInForum(forumId, topic);
        return "redirect:/foren/my-forums/" + forumIdLong;
    }
}
