package mops.foren.infrastructure.web.controller;

import mops.foren.infrastructure.web.TopicForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
@RequestMapping("/foren/topic")
public class TopicController {
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
}
