package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.TopicService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
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

    public TopicController(ForumService forumService, TopicService topicService) {
        this.forumService = forumService;
        this.topicService = topicService;
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
