package mops.foren.applicationservices;

import mops.foren.domain.model.ThreadId;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.domain.services.ThreadModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PostServiceTest {

    private IPostRepository postRepository;
    private PostService postService;
    private ThreadModelService threadModelService;

    /**
     * Set up test setting.
     */
    @BeforeEach
    public void setUp() {
        this.postRepository = mock(IPostRepository.class);
        this.threadModelService = mock(ThreadModelService.class);
        this.postService = new PostService(this.postRepository, this.threadModelService);
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
}
