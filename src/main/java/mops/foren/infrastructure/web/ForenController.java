package mops.foren.infrastructure.web;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.TopicService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
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
    TopicService topicService;
    ForumForm form = new ForumForm("", "");

    public ForenController(UserService userService, ForumService forumService, TopicService topicService) {
        this.userService = userService;
        this.forumService = forumService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }


    /**
     * Method checks if user is in the DB and gets all his forums.
     *
     * @param token Keyclock token.
     * @param model Content we add to html per thymleaf.
     * @return Get-mapping for my-forums.
     */
    @GetMapping("/my-forums")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String allForum(KeycloakAuthenticationToken token, Model model) {
        User user = userService.getUserFromDB(token);
        model.addAttribute("forums", forumService.getForums(user));
        model.addAttribute("forum", form);
        return "my-forums";
    }


    /**
     * Method creats a new Forum.
     *
     * @param token     Keyclock token.
     * @param forumForm A model attribute from thymleaf.
     * @param errors    Errors that occured during the parsing of the ForumForm.
     * @return A redirect to my-forums.
     */
    @PostMapping("/my-forums/newForum")
    @RolesAllowed({"ROLE_studentin", "ROLE_orga"})
    public String newForum(KeycloakAuthenticationToken token,
                           @ModelAttribute @Valid ForumForm forumForm, Errors errors) {
        //errors
        User user = userService.getUserFromDB(token);
        Forum forum = forumForm.getForum();
        userService.addForumInUser(user, forum);
        return "redirect:/my-forums";
    }

    @GetMapping("/my-forums/{forenID}")
    public String enterAForum(@PathVariable String forenID, Model model) {

        ForumId forumIdWrapped = new ForumId(Long.valueOf(forenID));

        model.addAttribute("topics", this.topicService.getTopics(forumIdWrapped));
        model.addAttribute("forum", this.forumService.getForum(forumIdWrapped));

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
