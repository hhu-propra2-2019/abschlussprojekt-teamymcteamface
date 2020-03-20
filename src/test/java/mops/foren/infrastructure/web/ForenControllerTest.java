package mops.foren.infrastructure.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static mops.foren.infrastructure.web.KeycloakTokenMock.setupTokenMock;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ForenControllerTest {

    @Autowired
    MockMvc mvcMock;

    @Autowired
    WebApplicationContext context;

    @MockBean
    KeycloakService keycloakServiceMock;

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
    void testForumMainpage() throws Exception {
        this.mvcMock.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testProfileTemplate() throws Exception {
        this.mvcMock.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

    @Test
    void testMyForumTemplateNotAuthenticated() throws Exception {
        this.mvcMock.perform(get("/my-forums"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sso/login"));
    }

    @Test
    void testMyForumTemplateAuthenticated() throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("studentin");
        Account account = new Account("studentin", "User@email.de", "image", roles);
        setupTokenMock(account);

        this.mvcMock.perform(get("/my-forums"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-forums"));
    }

    @Test
    void testMyForumTemplateAuthenticatedButUnauthorised() throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("wrong role");
        Account account = new Account("studentin", "User@email.de", "image", roles);
        setupTokenMock(account);

        this.mvcMock.perform(get("/my-forums"))
                .andExpect(status().isForbidden());
    }

}
