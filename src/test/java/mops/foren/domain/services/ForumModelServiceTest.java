package mops.foren.domain.services;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ForumModelServiceTest {

    private IThreadRepository threadRepository;
    private ForumModelService forumModelService;
    private Forum forum;
    private ForumId forumId = new ForumId(1L);

    /**
     * This method sets up the classes that need to be mocked.
     */
    @BeforeEach
    public void setUp() {
        this.threadRepository = mock(IThreadRepository.class);
        this.forumModelService = new ForumModelService(this.threadRepository);
        this.forum = Forum.builder()
                .id(this.forumId)
                .build();
    }


    @Test
    public void emptyThreadList() {
        // Arrange
        when(this.threadRepository.getThreadByForumIdAndVisible(this.forumId, true))
                .thenReturn(new LinkedList<>());


        // Act
        this.forumModelService.updateLastChangedTime(this.forum);

        //Assert
        assertThat(this.forum.getLastChange()).isNull();
    }

    @Test
    public void oneThreadInThreadList() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        when(this.threadRepository.getThreadByForumIdAndVisible(this.forumId, true))
                .thenReturn(makeThreadsForTest(now));


        // Act
        this.forumModelService.updateLastChangedTime(this.forum);

        //Assert
        assertThat(this.forum.getLastChange()).isEqualTo(now);
    }

    @Test
    public void threeThreadInThreadList() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beforeOneYear = now.minusYears(1L);
        LocalDateTime beforeOneHour = now.minusHours(1L);
        when(this.threadRepository.getThreadByForumIdAndVisible(this.forumId, true))
                .thenReturn(makeThreadsForTest(now, beforeOneHour, beforeOneYear));


        // Act
        this.forumModelService.updateLastChangedTime(this.forum);

        //Assert
        assertThat(this.forum.getLastChange()).isEqualTo(now);
    }

    @Test
    public void threeThreadInThreadListWithTheSameDate() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        when(this.threadRepository.getThreadByForumIdAndVisible(this.forumId, true))
                .thenReturn(makeThreadsForTest(now, now, now));


        // Act
        this.forumModelService.updateLastChangedTime(this.forum);

        //Assert
        assertThat(this.forum.getLastChange()).isEqualTo(now);
    }

    @Test
    public void oneThreadInThreadListWithNullAsDate() {
        // Arrange
        Thread thread = Thread.builder().lastPostTime(null).build();
        when(this.threadRepository.getThreadByForumIdAndVisible(this.forumId, true))
                .thenReturn(List.of(thread));


        // Act
        this.forumModelService.updateLastChangedTime(this.forum);

        //Assert
        assertThat(this.forum.getLastChange()).isNull();
    }

    private List<Thread> makeThreadsForTest(LocalDateTime... dates) {
        List<Thread> threads = new LinkedList<>();
        for (LocalDateTime date : dates) {
            threads.add(Thread.builder().lastPostTime(date).build());
        }
        return threads;
    }
}


