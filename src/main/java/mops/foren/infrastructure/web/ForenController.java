package mops.foren.infrastructure.web;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class ForenController {

    UserService userService;
    ForumService forumService;

    public ForenController(UserService userService, ForumService forumService) {
        this.userService = userService;
        this.forumService = forumService;
    }

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/my-forums")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String allForum(KeycloakAuthenticationToken token, Model model) {
        if (token != null) {
            String name = token.getName();
            if (userService.checkIfUserIsExistent(name) == false) {
                userService.addNewUser(token.getAccount().getPrincipal().getName());
            }
            User user = userService.getUser(token.getAccount().getPrincipal().getName());

            model.addAttribute("foren", forumService.getForums(user.getUserForums()));
            ForumForm form = new ForumForm("", "");
            model.addAttribute("forum", form);
        }
        return "my-forums";
    }

    @PostMapping("/my-forums/newForum")
    public String newForum(KeycloakAuthenticationToken token, @ModelAttribute ForumForm forumForm) {
        User user = userService.getUser(token.getAccount().getPrincipal().getName());
        Forum forum = Forum.builder().description(forumForm.description).title(forumForm.title).build();
        userService.addForum(user, forum);
        return "redirect:/my-forums";
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
