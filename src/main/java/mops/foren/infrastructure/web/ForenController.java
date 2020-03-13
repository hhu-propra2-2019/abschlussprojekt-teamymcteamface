package mops.foren.infrastructure.web;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ForenController {

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
        List<Forum> forums = new ArrayList<>();
        forums.add(new Forum(new ForumId(1L), "Algorithmen und Datenstrukturen", "Hi Ima Forum."));
        forums.add(new Forum(new ForumId(2L), "Softwareentwicklung im Team", "Hi Ima Forum."));
        forums.add(new Forum(new ForumId(3L), "TeamyMcTeamFace Beste Gruppe Bruder", ":)"));
        model.addAttribute("forums", forums);
        return "my-forums";
    }

    @GetMapping("/my-forums/{forenID}")
    public String enterAForum(@PathVariable String forenID) {
        return "/";
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
