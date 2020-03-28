package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.*;
import mops.foren.domain.model.Topic;
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

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class TopicControllerTests {
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
    ThreadService threadServiceMock;

    @MockBean
    TopicService topicServiceMock;

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
    void testEnterATopicWithoutPermission() throws Exception {
        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);


        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(userMock.checkPermission(any(), any())).thenReturn(false);
        when(topicServiceMock.getTopic(any())).thenReturn(Topic.builder().build());
        when(threadServiceMock.getThreadPageByVisibility(any(), any(), any())).thenReturn(null);
        when(threadServiceMock.countInvisibleThreads(any())).thenReturn(1);

        this.mvcMock.perform(get("/foren/topic/?topicId=1&page=0"))
                .andExpect(status().isOk())
                .andExpect(view().name("error-no-permission"));
    }
}
