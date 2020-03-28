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
        this.threadService.getThreads(topicId, page);

        //Assert
        verify(this.threadRepository).getThreadPageFromDB(topicId, page);
    }

    @Test
    public void testGetThreadDirectsTheCallCorrectly() {
        // Arrange
        ThreadId threadId = new ThreadId(0L);

        // Act
        this.threadService.getThreadById(threadId);

        //Assert
        verify(this.threadRepository).getThreadById(threadId);
    }


    @Test
    public void testAddPostInThreadDirectsTheCallCorrectly() {
        // Arrange
        ThreadId threadId = new ThreadId(0L);
        Post post = Post.builder().build();

        // Act
        this.threadService.addPostInThread(threadId, post);

        //Assert
        verify(this.threadRepository).addPostInThread(threadId, post);
    }

    @Test
    public void testGetThreadPageByVisibilityDirectsTheCallCorrectly() {
        // Arrang
        TopicId topicId = new TopicId(0L);
        Integer page = 1;
        Boolean visibility = true;

        // Act
        this.threadService.getThreadPageByVisibility(topicId, page, visibility);

        //Assert
        verify(this.threadRepository).getThreadPageFromDbByVisibility(topicId, page, visibility);
    }

    @Test
    public void testSetThreadVisibleDirectsTheCallCorrectly() {
        // Arrange
        ThreadId threadId = new ThreadId(0L);

        // Act
        this.threadService.setThreadVisible(threadId);

        //Assert
        verify(this.threadRepository).setThreadVisible(threadId);
    }

    @Test
    public void testCountInvisibleThreadsDirectsTheCallCorrectly() {
        // Arrange
        TopicId topicId = new TopicId(1L);

        // Act
        this.threadService.countInvisibleThreads(topicId);

        //Assert
        verify(this.threadRepository).countInvisibleThreads(topicId);
    }

    @Test
    public void testDeleteThreadDirectsTheCallCorrectly() {
        // Arrange
        ThreadId threadId = new ThreadId(1L);

        // Act
        this.threadService.deleteThread(threadId);

        //Assert
        verify(this.threadRepository).deleteThread(threadId);
    }


}
