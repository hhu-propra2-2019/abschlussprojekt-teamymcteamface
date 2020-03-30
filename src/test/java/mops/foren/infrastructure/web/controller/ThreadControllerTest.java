package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.ForumService;
import mops.foren.applicationservices.PostService;
import mops.foren.applicationservices.ThreadService;
import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.Paging;
import mops.foren.domain.model.paging.PostPage;
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

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ThreadControllerTest {

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
    User userMock;

    @BeforeEach
    public void setup() {
        this.mvcMock = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

        Account fakeAccount = Account.builder()
                .name("studentin")
                .roles(Set.of("studentin"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);
    }

    @Test
    public void testDisplayAThreadView() throws Exception {

        Paging paging = new Paging(false, false, false, 0, 0L, 0);
        PostPage postPage = new PostPage(paging, List.of());

        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(postServiceMock.getPosts(any(), any())).thenReturn(postPage);
        when(threadServiceMock.getThreadById(any()))
                .thenReturn(Thread.builder().id(new ThreadId(1L)).build());

        mvcMock.perform(get("/foren/thread?threadId=1&page=0&error=error"))
                .andExpect(status().isOk())
                .andExpect(view().name("thread"));
    }

    @Test
    public void testDisplayAThreadModel() throws Exception {

        Paging paging = new Paging(false, false, false, 0, 0L, 0);
        PostPage postPage = new PostPage(paging, List.of());
        Thread fakeThread = Thread.builder().id(new ThreadId(1L)).build();

        when(userServiceMock.getUserFromDB(any())).thenReturn(userMock);
        when(postServiceMock.getPosts(any(), any())).thenReturn(postPage);
        when(threadServiceMock.getThreadById(any()))
                .thenReturn(fakeThread);

        mvcMock.perform(get("/foren/thread?threadId=1&page=0&error=error"))
                .andExpect(model().attributeExists("error", "moderator",
                        "minContentLength", "maxContentLength", "form"))
                .andExpect(model().attribute("thread", fakeThread))
                .andExpect(model().attribute("posts", List.of()))
                .andExpect(model().attribute("pagingObject", paging))
                .andExpect(model().attribute("user", userMock));
    }

    @Test
    public void testNewThreadView() throws Exception {

        mvcMock.perform(get("/foren/thread/new-thread?topicId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-thread"));
    }

    @Test
    public void testNewThreadModel() throws Exception {

        mvcMock.perform(get("/foren/thread/new-thread?topicId=1"))
                .andExpect(model().attributeExists("minTitleLength", "maxTitleLength",
                        "minContentLength", "maxContentLength", "form"))
                .andExpect(model().attribute("topicId", 1L))
                .andExpect(model().attributeDoesNotExist("error"));
    }
}
