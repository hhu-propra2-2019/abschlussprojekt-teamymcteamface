package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.*;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.Paging;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import mops.foren.infrastructure.web.KeycloakTokenMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.mockito.Answers;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
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

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.postServiceMock.getPosts(any(), any())).thenReturn(postPage);
        when(this.threadServiceMock.getThreadById(any()))
                .thenReturn(Thread.builder().id(new ThreadId(1L)).build());

        this.mvcMock.perform(get("/foren/thread?threadId=1&page=0&error=error"))
                .andExpect(status().isOk())
                .andExpect(view().name("thread"));
    }

    @Test
    public void testDisplayAThreadModel() throws Exception {

        Paging paging = new Paging(false, false, false, 0, 0L, 0);
        PostPage postPage = new PostPage(paging, List.of());
        Thread fakeThread = Thread.builder().id(new ThreadId(1L)).build();

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.postServiceMock.getPosts(any(), any())).thenReturn(postPage);
        when(this.threadServiceMock.getThreadById(any()))
                .thenReturn(fakeThread);

        this.mvcMock.perform(get("/foren/thread?threadId=1&page=0&error=error"))
                .andExpect(model().attributeExists("error", "moderator",
                        "minContentLength", "maxContentLength", "form"))
                .andExpect(model().attribute("thread", fakeThread))
                .andExpect(model().attribute("posts", List.of()))
                .andExpect(model().attribute("pagingObject", paging))
                .andExpect(model().attribute("user", this.userMock));
    }

    @Test
    public void testNewThreadView() throws Exception {

        this.mvcMock.perform(get("/foren/thread/new-thread?topicId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-thread"));
    }

    @Test
    public void testNewThreadModel() throws Exception {

        when(this.topicServiceMock.getTopic(any())).thenReturn(Topic.builder()
                .title("").id(new TopicId(1L)).moderated(true).build());

        this.mvcMock.perform(get("/foren/thread/new-thread?topicId=1"))
                .andExpect(model().attributeExists("minTitleLength", "maxTitleLength",
                        "minContentLength", "maxContentLength", "form"))
                .andExpect(model().attribute("inModeratedTopic", true))
                .andExpect(model().attribute("topicTitle", ""))
                .andExpect(model().attribute("topicId", 1L))
                .andExpect(model().attributeDoesNotExist("error"));
    }

    @Test
    public void testAddAThreadWithoutPermissionView() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any())).thenReturn(false);
        when((this.topicServiceMock.getTopic(any()).getForumId())).thenReturn(new ForumId(1L));

        this.mvcMock.perform(post("/foren/thread/add-thread?topicId=1")
                .with(csrf())
                .param("title", "    ")
                .param("content", "    "))
                .andExpect(status().isOk())
                .andExpect(view().name("error-no-permission"));
    }

    @Test
    public void testAddAThreadWithPermissionView() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any())).thenReturn(true);
        when((this.topicServiceMock.getTopic(any()).getForumId())).thenReturn(new ForumId(1L));

        this.mvcMock.perform(post("/foren/thread/add-thread?topicId=1")
                .with(csrf())
                .param("title", "    ")
                .param("content", "    "))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/foren/topic/?topicId=1&page=1"));
    }

    @Test
    public void testAddAThreadWithPermissionInvokeAddThread() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any())).thenReturn(true);
        when((this.topicServiceMock.getTopic(any()).getForumId())).thenReturn(new ForumId(1L));

        this.mvcMock.perform(post("/foren/thread/add-thread?topicId=1")
                .with(csrf())
                .param("title", "    ")
                .param("content", "    "));

        verify(this.topicServiceMock).addThreadInTopic(any(), any());
    }

    @Test
    public void testAddAThreadWithPermissionBindingHasError() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any())).thenReturn(true);
        when((this.topicServiceMock.getTopic(any()).getForumId())).thenReturn(new ForumId(1L));

        this.mvcMock.perform(post("/foren/thread/add-thread?topicId=1")
                .with(csrf())
                .param("title", "")
                .param("content", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/foren/thread/new-thread?topicId=1"));
    }

    @Test
    public void testApproveAThreadWithoutPermissionView() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any())).thenReturn(false);
        when(this.threadServiceMock.getThreadById(any())).thenReturn(Thread.builder()
                .id(new ThreadId(1L))
                .forumId(new ForumId(1L)).build());

        this.mvcMock.perform(post("/foren/thread/approve-thread?threadId=1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error-no-permission"));
    }

    @Test
    public void testApproveAThreadWithPermissionView() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any())).thenReturn(true);
        when(this.threadServiceMock.getThreadById(any())).thenReturn(Thread.builder()
                .id(new ThreadId(1L))
                .forumId(new ForumId(1L)).build());

        this.mvcMock.perform(post("/foren/thread/approve-thread?threadId=1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/foren/thread?threadId=1&page=1"));
    }

    @Test
    public void testApproveAThreadWithPermissionInvokeApprove() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any())).thenReturn(true);
        when(this.threadServiceMock.getThreadById(any())).thenReturn(Thread.builder()
                .id(new ThreadId(1L))
                .forumId(new ForumId(1L)).build());

        this.mvcMock.perform(post("/foren/thread/approve-thread?threadId=1")
                .with(csrf()));

        verify(this.threadServiceMock).setThreadVisible(any());
    }

    @Test
    public void testDeleteAThreadWithoutPermissionView() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any(), any())).thenReturn(false);
        when(this.threadServiceMock.getThreadById(any())).thenReturn(Thread.builder()
                .id(new ThreadId(1L))
                .forumId(new ForumId(1L))
                .topicId(new TopicId(1L)).build());

        this.mvcMock.perform(post("/foren/thread/delete-thread?threadId=1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error-no-permission"));
    }

    @Test
    public void testDeleteAThreadWithPermissionView() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any(), any())).thenReturn(true);
        when(this.threadServiceMock.getThreadById(any())).thenReturn(Thread.builder()
                .id(new ThreadId(1L))
                .forumId(new ForumId(1L))
                .topicId(new TopicId(1L)).build());

        this.mvcMock.perform(post("/foren/thread/delete-thread?threadId=1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/foren/topic?topicId=1&page=1"));
    }

    @Test
    public void testDeleteAThreadWithPermissionInvokeDelete() throws Exception {

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.userMock.checkPermission(any(), any(), any())).thenReturn(true);
        when(this.threadServiceMock.getThreadById(any())).thenReturn(Thread.builder()
                .id(new ThreadId(1L))
                .forumId(new ForumId(1L))
                .topicId(new TopicId(1L)).build());

        this.mvcMock.perform(post("/foren/thread/delete-thread?threadId=1")
                .with(csrf()));

        verify(this.threadServiceMock).deleteThread(any());
    }

    @Test
    void testAccountIsInModel() throws Exception {
        Account fakeAccount = Account.builder()
                .name("orga")
                .roles(Set.of("orga"))
                .build();
        KeycloakTokenMock.setupTokenMock(fakeAccount);

        Paging paging = new Paging(false, false, false, 0, 0L, 0);
        PostPage postPage = new PostPage(paging, List.of());

        when(this.userServiceMock.getUserFromDB(any())).thenReturn(this.userMock);
        when(this.postServiceMock.getPosts(any(), any())).thenReturn(postPage);
        when(this.threadServiceMock.getThreadById(any()))
                .thenReturn(Thread.builder().id(new ThreadId(1L)).build());
        when(this.keycloakServiceMock.createAccountFromPrincipal(
                any(KeycloakAuthenticationToken.class))).thenReturn(fakeAccount);

        this.mvcMock.perform(get("/foren/thread?threadId=1&page=0&error=error"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", fakeAccount));
    }
}

