package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.TopicService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Permission;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@SessionScope
@RequestMapping({"/foren/my-forums", "/foren"})
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class ForumController {

    private UserService userService;
    private ForumService forumService;
    private PostService postService;
    private TopicService topicService;
    private KeycloakService keycloakService;

    /**
     * Constructor for ForenController. The parameters that are injected.
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
     * @param model Content we add to html per Thymeleaf.
     * @return Get-mapping for my-forums.
     */
    @GetMapping
    public String allForums(KeycloakAuthenticationToken token,
                            Model model) {

        User user = this.userService.getUserFromDB(token);
        List<Forum> forums = this.forumService.getForums(user);
        forums.forEach(forum -> this.forumService.updateLastTimeChanged(forum));

        model.addAttribute("forums", forums);

        return "my-forums";
    }

    /**
     * Mapping to the main-page of a certain forum.
     *
     * @param token       - the keycloak token
     * @param forumIdLong - id of the forum
     * @param model       - Model
     * @return The template for the forum main page.
     */
    @GetMapping("/enter")
    public String enterAForum(KeycloakAuthenticationToken token,
                              @RequestParam("forumId") Long forumIdLong,
                              Model model) {

        User user = this.userService.getUserFromDB(token);
        ForumId forumId = new ForumId(forumIdLong);

        if (user.checkPermission(forumId, Permission.READ_FORUM)) {
            model.addAttribute("topics", this.topicService.getTopics(forumId));
            model.addAttribute("forum", this.forumService.getForum(forumId));
            model.addAttribute("forumId", forumIdLong);
            model.addAttribute("createPermission", user.checkPermission(
                    forumId, Permission.CREATE_TOPIC));
            model.addAttribute("permission", user.checkPermission(
                    forumId, Permission.DELETE_TOPIC));
            return "forum-mainpage";
        }
        return "error-no-permission";

    }

    /**
     * Search all posts in one forum by a text search.
     *
     * @param token         keycloak token
     * @param forumIdLong   the ID of the forum you want to search all posts
     * @param searchContent the text the user searches for
     * @param page          number of page in the paging system
     * @param model         model
     * @return The template.
     */
    @GetMapping("/search")
    public String searchForum(KeycloakAuthenticationToken token,
                              @RequestParam("forumId") Long forumIdLong,
                              @RequestParam("searchContent") String searchContent,
                              @RequestParam("page") Integer page,
                              Model model) {

        User user = this.userService.getUserFromDB(token);
        ForumId forumId = new ForumId(forumIdLong);

        if (user.checkPermission(forumId, Permission.READ_FORUM)) {
            PostPage postPage = this.postService.searchWholeForum(forumId, searchContent, page - 1);

            model.addAttribute("pagingObject", postPage.getPaging());
            model.addAttribute("posts", postPage.getPosts());
            model.addAttribute("content", searchContent);
            model.addAttribute("forumId", forumId.getId());
            return "search-result-posts";
        }
        return "error-no-permission";
    }

    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     *
     * @param token - KeycloakAuthenticationToken
     * @return Keycloak Account
     */
    @ModelAttribute("account")
    public Account addAccountToTheRequest(KeycloakAuthenticationToken token) {
        if (token == null) {
            return null;
        }

        return this.keycloakService.createAccountFromPrincipal(token);
    }
}
