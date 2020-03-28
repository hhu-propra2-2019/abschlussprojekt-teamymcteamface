package mops.foren.applicationservices;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PostServiceTest {

    private IPostRepository postRepository;
    private PostService postService;

    /**
     * Set up test setting.
     */
    @BeforeEach
    public void setUp() {
        this.postRepository = mock(IPostRepository.class);
        this.postService = new PostService(this.postRepository);
    }

    @Test
    public void testGetPostsDirectsTheCallCorrectly() {
        //Arrange
        ThreadId threadId = new ThreadId(0L);
        Integer page = 3;

        //Act
        this.postService.getPosts(threadId, page);

        //Assert
        verify(this.postRepository).getPostPageFromDB(threadId, page);
    }


    @Test
    public void testGetPostDirectsTheCallCorrectly() {
        //Arrange
        PostId postId = new PostId(1L);

        //Act
        this.postService.getPost(postId);

        //Assert
        verify(this.postRepository).getPostById(postId);
    }

    @Test
    public void testDeletePostDirectsTheCallCorrectly() {
        //Arrange
        PostId postId = new PostId(1L);

        //Act
        this.postService.deletePost(postId);

        //Assert
        verify(this.postRepository).deletePostById(postId);
    }

    @Test
    public void testSearchWholeForumDirectsTheCallCorrectly() {
        //Arrange
        ForumId forumId = new ForumId(1L);
        String content = "Hallo ich bin ein test";
        Integer page = 1;

        //Act
        this.postService.searchWholeForum(forumId, content, page);

        //Assert
        verify(this.postRepository).searchWholeForumForContent(forumId, content, page);
    }

    @Test
    public void testSetPostVisibleDirectsTheCallCorrectly() {
        //Arrange
        PostId postId = new PostId(1L);

        //Act
        this.postService.setPostVisible(postId);

        //Assert
        verify(this.postRepository).setPostVisible(postId);
    }
}
