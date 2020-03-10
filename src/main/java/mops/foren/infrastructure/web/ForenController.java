package mops.foren.infrastructure.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/foren", method = {GET, POST})
public class ForenController {

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/myForum")
    public String allForum() {
        return "myForums";
    }

    @GetMapping("/myForum/{forenID}")
    public String enterAForum(@PathVariable String forenID) {
        return "/";
    }

    @GetMapping("/{forenID}/{topicID}")
    public String enterATopic(@PathVariable String forenID, @PathVariable String topicID) {
        return "/";
    }

    @GetMapping("/{forenID}/{topicID}/newThread")
    public String createNewThread(@PathVariable String forenID, @PathVariable String topicID) {
        return "createThread";
    }
}
