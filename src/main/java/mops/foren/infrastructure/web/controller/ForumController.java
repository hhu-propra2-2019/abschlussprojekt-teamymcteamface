package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.TopicService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.web.ForumForm;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;

@Controller
@SessionScope
@RequestMapping("/foren/my-forums")
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class ForumController {

    private UserService userService;
    private ForumService forumService;
    private TopicService topicService;

    /**
     * Constructor for ForenController. The parameters are injected.
     *
     * @param userService  - injected UserService (ApplicationService)
     * @param forumService - injected ForumService (ApplicationService)
     * @param topicService - TopicService (ApplicationService)
     */
    public ForumController(UserService userService, ForumService forumService,
                           TopicService topicService) {
        this.userService = userService;
        this.forumService = forumService;
        this.topicService = topicService;
    }


    /**
     * Method checks if user is in the DB and gets all his forums.
     *
     * @param token Keycloak token.
     * @param model Content we add to html per thymleaf.
     * @return Get-mapping for my-forums.
     */
    @GetMapping
    public String allForum(KeycloakAuthenticationToken token,
                           Model model) {
        User user = this.userService.getUserFromDB(token);
        model.addAttribute("forums", this.forumService.getForums(user));
        model.addAttribute("forum", new ForumForm("", ""));
        return "my-forums";
    }

    /**
     * Mapping to the main-page of a certain forum.
     *
     * @param forenID - id of the forum
     * @param model   - Model
     * @return The template for the forum main page
     */
    @GetMapping("/{forenID}")
    public String enterAForum(@PathVariable String forenID,
                              Model model) {

        ForumId forumIdWrapped = new ForumId(Long.valueOf(forenID));

        model.addAttribute("topics", this.topicService.getTopics(forumIdWrapped));
        model.addAttribute("forum", this.forumService.getForum(forumIdWrapped));

        return "forum-mainpage";
    }
}
