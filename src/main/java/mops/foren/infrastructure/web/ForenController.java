package mops.foren.infrastructure.web;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.TopicService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ForenController {

    private User user = new User("Test Boi");
    private ForumService forumService = new ForumService(null);
    private TopicService topicService = new TopicService(null);
    private ThreadService threadService = new ThreadService(null);
    private PostService postService = new PostService(null);

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/my-forums")
    public String allForum(Model model) {
        model.addAttribute("forums", this.forumService.getMockForums(this.user));
        return "my-forums";
    }

    /**
     * yolo.
     *
     * @param forenID hello
     * @param model   hello
     * @return nix
     */
    @GetMapping("/my-forums/{forenID}")
    public String enterAForum(@PathVariable String forenID, Model model) {
        model.addAttribute("forum", this.forumService.getMockForum(
                new ForumId(Long.valueOf(forenID))));
        model.addAttribute("topics", this.topicService.getMockTopics(new ForumId(1L)));
        return "forum-mainpage";
    }

    @GetMapping("/my-forums/{forenID}/{topicID}")
    public String enterATopic(@PathVariable String forenID, @PathVariable String topicID) {
        return "/";
    }

    @GetMapping("/my-forums/{forenID}/{topicID}/newThread")
    public String createNewThread(@PathVariable String forenID, @PathVariable String topicID) {
        return "createThread";
    }
}
