package mops.foren.infrastructure.web;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.TopicService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    /**
     * Redirect the user to a certain list of topics.
     * @param forumIdAsString - Forum ID
     * @param topicIdAsString - Topic ID
     * @param model - Model sent to the user
     * @return - a redirect to the requested page
     */
    @GetMapping("/my-forums/{forumID}/{topicID}")
    public String enterATopic(@PathVariable String forumID, @PathVariable String topicID, Model model) {

        ForumId forumId = new ForumId(Long.valueOf(forumID));
        String forumTitle = this.forumService.getMockForum(forumId).getTitle();
        model.addAttribute("forumTitle", forumTitle);

        TopicId topicId = new TopicId(Long.parseLong(topicID));
        List<Thread> threads = this.threadService.getMockThreads(topicId);

        model.addAttribute("threads", threads);

        return "list-threads";
    }

    /*@GetMapping("/my-forums/{forenID}/{topicID}/new-thread")
    public String createNewThread(@PathVariable String forenID, @PathVariable String topicIdAsString, @PathVariable String topicID) {

        return "createThread";
    }*/

    @PostMapping("/my-forums/{forenID}/{topicID}/create-thread")
        public String postThreadIntoSystem() {
            return "redirect:/{forenID}/{topicID}";
        }
}
