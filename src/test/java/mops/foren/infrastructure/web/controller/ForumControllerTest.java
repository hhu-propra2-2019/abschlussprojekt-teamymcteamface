package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.PermissionManager;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import mops.foren.infrastructure.web.KeycloakTokenMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
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
        User fakeUser = User.builder().name("orga").build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        when(userServiceMock.getUserFromDB(any())).thenReturn(fakeUser);
        when(forumServiceMock.getForums(fakeUser)).thenReturn(new LinkedList<>());

        this.mvcMock.perform(get("/foren"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-forums"));
    }

    @Test
    void testEnterAForumWithoutPermission() throws Exception {
        //Arrange
        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        User fakeUser = User.builder()
                .name("studentin")
                .permissionManager(new PermissionManager())
                .forums(new LinkedList<>())
                .build();
        when(userServiceMock.getUserFromDB(any())).thenReturn(fakeUser);

        this.mvcMock.perform(get("/foren/my-forums/enter?forumId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error-no-permission"));
    }

}
