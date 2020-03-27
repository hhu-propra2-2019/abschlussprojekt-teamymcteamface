package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.Paging;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import mops.foren.infrastructure.web.KeycloakTokenMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForumControllerTest {

    @Autowired
    MockMvc mvcMock;

    @Autowired
    WebApplicationContext context;

    @MockBean
    KeycloakService keycloakServiceMock;

    @MockBean
    UserService userServiceMock;

    @MockBean
    ForumService forumServiceMock;

    @MockBean
    PostService postServiceMock;

    @MockBean
    User userMock;

    /**
     * Building up a security environment for the Test.
     */
    @BeforeEach
    public void setup() {
        this.mvcMock = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testMyForumTemplateNotAuthenticated() throws Exception {
        this.mvcMock.perform(get("/foren/my-forums"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sso/login"));
    }

    @Test
    void testMyForumTemplateAuthenticated() throws Exception {
        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        User fakeUser = User.builder().name("studentin").build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        when(userServiceMock.getUserFromDB(any())).thenReturn(fakeUser);
        when(forumServiceMock.getForums(fakeUser)).thenReturn(new LinkedList<>());

        this.mvcMock.perform(get("/foren/my-forums"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("forums"))
                .andExpect(view().name("my-forums"));
    }

    @Test
    void testMyForumTemplateAuthenticatedButUnauthorised() throws Exception {
        KeycloakTokenMock.setupTokenMock(Account.builder()
                .name("studentin")
                .roles(Set.of("wrong Role"))
                .build());

        this.mvcMock.perform(get("/foren/my-forums"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testMyForumLandingPage() throws Exception {
        Account fakeAccount = Account.builder()
                .name("orga")
                .roles(Set.of("orga"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(forumServiceMock.getForums(any())).thenReturn(new LinkedList<>());

        this.mvcMock.perform(get("/foren"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-forums"));
    }

    @Test
    void testAccountIsInModel() throws Exception {
        Account fakeAccount = Account.builder()
                .name("orga")
                .roles(Set.of("orga"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        when(keycloakServiceMock.createAccountFromPrincipal(any(KeycloakAuthenticationToken.class)))
                .thenReturn(fakeAccount);

        mvcMock.perform(get("/foren"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", fakeAccount));
    }

    @Test
    void testEnterAForumWithoutPermission() throws Exception {
        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(userMock.checkPermission(any(), any())).thenReturn(false);

        this.mvcMock.perform(get("/foren/my-forums/enter?forumId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error-no-permission"));
    }

    @Test
    void testEnterAForumWithPermissionRouting() throws Exception {
        //Arrange
        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        Forum fakeForum = Forum.builder().title("").build();
        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(userMock.checkPermission(any(), any())).thenReturn(true);
        when(forumServiceMock.getForum(any())).thenReturn(fakeForum);

        this.mvcMock.perform(get("/foren/my-forums/enter?forumId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("forum-mainpage"));
    }

    @Test
    void testEnterAForumWithPermissionModel() throws Exception {
        //Arrange
        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        Forum fakeForum = Forum.builder().title("").build();
        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(userMock.checkPermission(any(), any())).thenReturn(true);
        when(forumServiceMock.getForum(any())).thenReturn(fakeForum);

        this.mvcMock.perform(get("/foren/my-forums/enter?forumId=1"))
                .andExpect(model().attribute("forum", fakeForum))
                .andExpect(model().attribute("forumId", 1L))
                .andExpect(model().attribute("createPermission", true))
                .andExpect(model().attribute("permission", true));
    }

    @Test
    void testSearchAForumWithoutPermission() throws Exception {
        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(userMock.checkPermission(any(), any())).thenReturn(false);

        this.mvcMock.perform(get("/foren/my-forums/search?forumId=1&searchContent=hallo&page=0"))
                .andExpect(status().isOk())
                .andExpect(view().name("error-no-permission"));
    }

    @Test
    void testSearchWithPermission() throws Exception {
        //Arrange
        Account fakeAccount = Account.builder()
                .name("orga")
                .roles(Set.of("orga"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        Paging paging = new Paging(true, true, false, 0, 0L, 0);
        PostPage postPage = new PostPage(paging, List.of());

        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(userMock.checkPermission(any(), any())).thenReturn(true);
        when(postServiceMock.searchWholeForum(any(), any(), any())).thenReturn(postPage);

        this.mvcMock.perform(get("/foren/my-forums/search?forumId=1&searchContent=hallo&page=0"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-result-posts"));
    }

    @Test
    void testSearchModelWithPermission() throws Exception {
        Account fakeAccount = Account.builder()
                .name("orga")
                .roles(Set.of("orga"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        Paging paging = new Paging(true, true, false, 0, 0L, 0);
        PostPage postPage = new PostPage(paging, List.of());

        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(userMock.checkPermission(any(), any())).thenReturn(true);
        when(postServiceMock.searchWholeForum(any(), any(), any())).thenReturn(postPage);

        this.mvcMock.perform(get("/foren/my-forums/search?forumId=1&searchContent=hallo&page=0"))
                .andExpect(model().attribute("posts", List.of()))
                .andExpect(model().attribute("pagingObject", paging))
                .andExpect(model().attribute("content", "hallo"))
                .andExpect(model().attribute("forumId", 1L));
    }
}


