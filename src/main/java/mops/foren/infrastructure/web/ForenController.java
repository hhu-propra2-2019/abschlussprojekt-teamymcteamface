package mops.foren.infrastructure.web;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@SessionScope
public class ForenController {

    UserService userService;
    ForumService forumService;
    ForumForm form = new ForumForm("", "");

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
        User user = getUserFromDB(token);
        model.addAttribute("forums", forumService.getForums(user));
        model.addAttribute("forum", form);
        return "my-forums";
    }


    @PostMapping("/my-forums/newForum")
    public String newForum(KeycloakAuthenticationToken token, @ModelAttribute @Valid ForumForm forumForm, Errors errors) {
        //errors
        User user = getUserFromDB(token);
        Forum forum = forumForm.map();
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

    private User getUserFromToken(KeycloakAuthenticationToken token) {
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        String name = principal.getName();
        String email = principal.getKeycloakSecurityContext().getIdToken().getEmail();
        User user = User.builder()
                .name(name)
                .email(email)
                .build();
        return user;
    }

    private User getUserFromDB(KeycloakAuthenticationToken token) {
        User user = getUserFromToken(token);
        if (userService.checkIfUserIsExistent(user) == false) {
            userService.addNewUser(user);
        }
        return userService.getUser(user);
    }

}
