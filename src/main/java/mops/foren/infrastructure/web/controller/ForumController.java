package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.TopicService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Permission;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.ForumForm;
import mops.foren.infrastructure.web.KeycloakService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;

@Controller
@SessionScope
@RequestMapping("/foren/my-forums")
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class ForumController {

    private UserService userService;
    private ForumService forumService;
    private PostService postService;
    private TopicService topicService;
    private KeycloakService keycloakService;

    /**
     * Constructor for ForenController. The parameters are injected.
     *
     * @param userService     - injected UserService (ApplicationService)
     * @param forumService    - injected ForumService (ApplicationService)
     * @param topicService    - TopicService (ApplicationService)
     * @param keycloakService - KeycloakService (Infrastructure Service)
     */
    public ForumController(UserService userService, ForumService forumService,
                           TopicService topicService, PostService postService,
                           KeycloakService keycloakService) {
        this.userService = userService;
        this.forumService = forumService;
        this.topicService = topicService;
        this.postService = postService;
        this.keycloakService = keycloakService;
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
     * @param token   - the keycloak token
     * @param forenID - id of the forum
     * @param model   - Model
     * @return The template for the forum main page
     */
    @GetMapping("/{forenID}")
    public String enterAForum(KeycloakAuthenticationToken token,
                              @PathVariable String forenID,
                              Model model) {
        User user = this.userService.getUserFromDB(token);
        ForumId forumIdWrapped = new ForumId(Long.valueOf(forenID));
        if (!user.checkPermission(forumIdWrapped, Permission.READ_FORUM)) {
            return "error-no-permission";
        }
        model.addAttribute("topics", this.topicService.getTopics(forumIdWrapped));
        model.addAttribute("forum", this.forumService.getForum(forumIdWrapped));

        return "forum-mainpage";
    }

    /**
     * Search all posts in one forum by a text search.
     *
     * @param token   keycloak token
     * @param forenID Id of the forum you want to search all posts
     * @param content the text the user searches for
     * @param page    number of page in the paging system
     * @param model   model
     * @return The template.
     */
    @GetMapping("/{forenID}/search")
    public String searchForum(KeycloakAuthenticationToken token,
                              @PathVariable Long forenID,
                              @RequestParam String content,
                              @RequestParam Integer page,
                              Model model) {
        User user = this.userService.getUserFromDB(token);
        if (!user.checkPermission(new ForumId(forenID), Permission.READ_FORUM)) {
            return "error";
        }
        PostPage postPage = this.postService.searchWholeForum(new ForumId(forenID), content,
                page - 1);

        model.addAttribute("pagingObject", postPage.getPaging());
        model.addAttribute("posts", postPage.getPosts());
        model.addAttribute("content", content);
        return "search-result-posts";
    }

    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenficationToken
     * @return
     */
    @ModelAttribute("account")

    public Account addAccountToTheRequest(KeycloakAuthenticationToken token) {
        if (token == null) {
            return null;
        }

        return this.keycloakService.createAccountFromPrincipal(token);
    }
}
