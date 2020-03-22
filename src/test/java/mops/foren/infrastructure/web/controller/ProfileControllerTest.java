package mops.foren.infrastructure.web.controller;

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

import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ProfileControllerTest {
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
    void testProfileUnauthenticated() throws Exception {

        this.mvcMock.perform(get("/foren/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sso/login"));
    }

    @Test
    void testProfileAuthenticated() throws Exception {
        KeycloakTokenMock.setupTokenMock(Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build());

        this.mvcMock.perform(get("/foren/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

    @Test
    void testProfileWrongUser() throws Exception {
        KeycloakTokenMock.setupTokenMock(Account.builder()
                .name("orga1")
                .roles(Set.of("wrong role"))
                .build());

        this.mvcMock.perform(get("/foren/profile"))
                .andExpect(status().isForbidden());
    }
}