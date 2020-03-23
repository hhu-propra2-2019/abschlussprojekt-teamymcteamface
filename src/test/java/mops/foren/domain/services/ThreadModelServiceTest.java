package mops.foren.domain.services;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class ThreadModelServiceTest {

    private IPostRepository postRepository;
    private IThreadRepository threadRepository;
    private ThreadModelService threadModelService;
    private Thread threadMock;
    private ThreadId threadId = new ThreadId(0L);

    /**
     * This method set up the classes that need to be mocked.
     */
    @BeforeEach
    public void setUp() {
        this.postRepository = mock(IPostRepository.class);
        this.threadRepository = mock(IThreadRepository.class);
        this.threadModelService = new ThreadModelService(postRepository, threadRepository);

        this.threadMock = mock(Thread.class);
        when(threadRepository.getThreadById(this.threadId))
                .thenReturn(threadMock);

    }

    @Test
    public void emptyPostsList() {
        // Arrange
        when(postRepository.getAllPostsByThreadId(this.threadId))
                .thenReturn(new LinkedList<>());

        // Act
        threadModelService.updateLastPostTime(this.threadId);

        //Assert
        verify(threadMock).setLastPostTime(null);
    }

    @Test
    public void OnePostInThread() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        List<Post> posts = buildPostWithTime(now);

        when(postRepository.getAllPostsByThreadId(this.threadId))
                .thenReturn(posts);

        // Act
        threadModelService.updateLastPostTime(this.threadId);

        //Assert
        verify(threadMock).setLastPostTime(now);
    }


    @Test
    public void ThreePostsInThread() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yearBefore = now.minusYears(1);
        LocalDateTime hourBefore = now.minusHours(1);
        List<Post> posts = buildPostWithTime(now, yearBefore, hourBefore);
        when(postRepository.getAllPostsByThreadId(this.threadId))
                .thenReturn(posts);

        // Act
        threadModelService.updateLastPostTime(this.threadId);
        //Assert
        verify(threadMock).setLastPostTime(now);
    }


    private List<Post> buildPostWithTime(LocalDateTime... dates) {
        List<LocalDateTime> dateList = List.of(dates);
        return dateList.stream()
                .map(date -> Post.builder()
                        .creationDate(date)
                        .build())
                .collect(Collectors.toList());
    }
}
