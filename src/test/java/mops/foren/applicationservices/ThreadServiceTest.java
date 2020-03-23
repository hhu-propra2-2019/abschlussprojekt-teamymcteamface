package mops.foren.applicationservices;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ThreadServiceTest {

    private IThreadRepository threadRepository;
    private ThreadService threadService;

    @BeforeEach
    public void setUp() {
        this.threadRepository = mock(IThreadRepository.class);
        this.threadService = new ThreadService(this.threadRepository);
    }

    @Test
    public void testGetThreadPageWithTopicIdDirectsTheCallCorrectly() {
        // Arrange
        TopicId topicId = new TopicId(0L);
        Integer page = 0;

        // Act
        threadService.getThreadPageWithTopicId(topicId, page);

        //Assert
        verify(threadRepository).getThreadPageFromDB(topicId, page);
    }

    @Test
    public void testGetThreadDirectsTheCallCorrectly() {
        // Arrange
        ThreadId threadId = new ThreadId(0L);

        // Act
        threadService.getThreadById(threadId);

        //Assert
        verify(threadRepository).getThreadById(threadId);
    }


    @Test
    public void testAddPostInThreadDirectsTheCallCorrectly() {
        // Arrange
        ThreadId threadId = new ThreadId(0L);
        Post post = Post.builder().build();

        // Act
        threadService.addPostInThread(threadId, post);

        //Assert
        verify(threadRepository).addPostInThread(threadId, post);
    }
}
