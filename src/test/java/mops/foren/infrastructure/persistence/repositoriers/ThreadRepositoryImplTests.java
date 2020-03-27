package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.ThreadPage;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ThreadRepositoryImplTests {

    /**
     * Repository under test.
     */
    private final ThreadRepositoryImpl threadRepositoryImpl;

    /**
     * Jpa thread repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final ThreadJpaRepository threadJpaRepository;

    /**
     * Jpa topic repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final TopicJpaRepository topicJpaRepository;

    /**
     * Jpa post repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final PostJpaRepository postJpaRepository;


    /**
     * Jpa user repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final UserJpaRepository userJpaRepository;

    private Topic topicWithTwoThreads;
    private Thread visibleThreadInTopicWithTwoThreads;
    private Thread notVisibleThreadInTopicWithTwoThreads;
    private Thread threadWithTwoPosts;
    private User userInThreadWithTwoPosts;

    /**
     * Constructor for repo injection.
     *
     * @param threadJpaRepository  injected threadJpaRepository
     * @param threadRepositoryImpl injected threadRepositoryImpl
     * @param topicJpaRepository   injected topicJpaRepository
     * @param postJpaRepository    injected postJpaRepository
     * @param userJpaRepository    injected userJpaRepository
     */
    @Autowired
    public ThreadRepositoryImplTests(ThreadJpaRepository threadJpaRepository,
                                     ThreadRepositoryImpl threadRepositoryImpl,
                                     TopicJpaRepository topicJpaRepository,
                                     PostJpaRepository postJpaRepository,
                                     UserJpaRepository userJpaRepository) {
        this.threadJpaRepository = threadJpaRepository;
        this.threadRepositoryImpl = threadRepositoryImpl;
        this.topicJpaRepository = topicJpaRepository;
        this.postJpaRepository = postJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    /**
     * Set up method with injection. SetsUp the tests.
     */
    @BeforeEach
    public void setUp() {

        this.topicWithTwoThreads = TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(2L).get());
        this.threadWithTwoPosts = ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());
        this.userInThreadWithTwoPosts = UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user2").get());
        this.visibleThreadInTopicWithTwoThreads =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(1L).get());
        this.notVisibleThreadInTopicWithTwoThreads =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());
    }

    @Test
    public void testIfThreadPageCanBeLoadedForTopicFromDatabase() {
        // Act
        ThreadPage loadedThreads = this.threadRepositoryImpl.getThreadPageFromDB(this.topicWithTwoThreads.getId(), 0);
        int entryCount = loadedThreads.getThreads().size();

        // Assert
        assertThat(entryCount).isEqualTo(2);
    }

    @Test
    public void testIfThreadCanBeLoadedFromDatabaseById() {
        // Act
        Thread loadedThread = this.threadRepositoryImpl.getThreadById(this.threadWithTwoPosts.getId());
        // Assert
        assertThat(loadedThread).isEqualTo(this.threadWithTwoPosts);
    }

    @Test
    public void testIfPostCanBeAddedToThread() {
        // Arrange
        final String postText = "unique text";

        Post newPost = Post.builder()
                .text(postText)
                .author(this.userInThreadWithTwoPosts)
                .creationDate(LocalDateTime.now())
                .threadId(this.threadWithTwoPosts.getId())
                .forumId(this.threadWithTwoPosts.getForumId())
                .visible(false)
                .anonymous(false)
                .changed(false)
                .build();

        // Act
        this.threadRepositoryImpl.addPostInThread(this.threadWithTwoPosts.getId(), newPost);
        // load updated Thread
        Boolean postWasAdded = this.postJpaRepository.existsByText(postText);
        // Arrange
        assertThat(postWasAdded).isTrue();
    }

    @Test
    public void testIfOnlyVisibleThreadsCanBePaged() {
        // Act
        ThreadPage loadedPage = this.threadRepositoryImpl.getThreadPageFromDbByVisibility(
                this.topicWithTwoThreads.getId(), 0, true);
        List<Thread> loadedThreads = loadedPage.getThreads();
        // Assert
        assertThat(loadedThreads).containsExactly(this.visibleThreadInTopicWithTwoThreads);
    }

    @Test
    public void testIfOnlyNotVisibleThreadsCanBePaged() {
        // Act
        ThreadPage loadedPage = this.threadRepositoryImpl.getThreadPageFromDbByVisibility(
                this.topicWithTwoThreads.getId(), 0, false);
        List<Thread> loadedThreads = loadedPage.getThreads();
        // Assert
        assertThat(loadedThreads).containsExactly(this.notVisibleThreadInTopicWithTwoThreads);
    }

    @Test
    public void testIfVisibilityCanBeSet() {
        // Act
        this.threadRepositoryImpl.setThreadVisible(this.notVisibleThreadInTopicWithTwoThreads.getId());
        // load updated Thread
        Thread updatedThread = ThreadMapper.mapThreadDtoToThread(
                this.threadJpaRepository.findById(this.notVisibleThreadInTopicWithTwoThreads.getId().getId()).get());
        Boolean isVisible = updatedThread.getVisible();
        // Assert
        assertThat(isVisible).isTrue();
    }

    @Test
    public void testIfInvisibleThreadsAreCountedCorrectly() {
        // Act
        int notVisibleCount = this.threadRepositoryImpl.countInvisibleThreads(this.topicWithTwoThreads.getId());
        // Assert
        assertThat(notVisibleCount).isEqualTo(1);
    }

    @Test
    public void test() {
        // Act
        this.threadRepositoryImpl.deleteThread(this.threadWithTwoPosts.getId());
        Boolean wasDeleted = !this.threadJpaRepository.existsById(this.threadWithTwoPosts.getId().getId());
        // Assert
        assertThat(wasDeleted).isTrue();
    }
}
