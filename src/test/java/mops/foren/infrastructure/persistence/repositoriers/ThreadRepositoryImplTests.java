package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.ThreadPage;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.PostJpaRepository;
import mops.foren.infrastructure.persistence.repositories.ThreadJpaRepository;
import mops.foren.infrastructure.persistence.repositories.TopicJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
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

    private final IThreadRepository threadRepositoryImpl;
    private final ThreadJpaRepository threadJpaRepository;
    private final TopicJpaRepository topicJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

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
                                     IThreadRepository threadRepositoryImpl,
                                     TopicJpaRepository topicJpaRepository,
                                     PostJpaRepository postJpaRepository,
                                     UserJpaRepository userJpaRepository) {
        this.threadJpaRepository = threadJpaRepository;
        this.threadRepositoryImpl = threadRepositoryImpl;
        this.topicJpaRepository = topicJpaRepository;
        this.postJpaRepository = postJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Test
    public void testIfThreadPageCanBeLoadedForTopicFromDatabase() {
        // Arrange
        Topic topicWithTwoThreads =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(2L).get());

        // Act
        ThreadPage loadedThreads =
                this.threadRepositoryImpl.getThreadPageFromDB(topicWithTwoThreads.getId(), 0);

        int entryCount = loadedThreads.getThreads().size();

        // Assert
        assertThat(entryCount).isEqualTo(2);
    }

    @Test
    public void testIfThreadCanBeLoadedFromDatabaseById() {
        // Arrange
        Thread threadInDatabase =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());

        // Act
        Thread loadedThread =
                this.threadRepositoryImpl.getThreadById(threadInDatabase.getId());

        // Assert
        assertThat(loadedThread).isEqualTo(threadInDatabase);
    }

    @Test
    public void testIfPostCanBeAddedToThread() {
        // Arrange
        final String postText = "unique text";

        Thread threadWithTwoPosts =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());

        User userInThreadWithTwoPosts =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user2").get());

        Post newPost = Post.builder()
                .text(postText)
                .author(userInThreadWithTwoPosts)
                .creationDate(LocalDateTime.now())
                .threadId(threadWithTwoPosts.getId())
                .forumId(threadWithTwoPosts.getForumId())
                .visible(false)
                .anonymous(false)
                .changed(false)
                .build();

        // Act
        this.threadRepositoryImpl.addPostInThread(threadWithTwoPosts.getId(), newPost);

        // load updated Thread
        Boolean postWasAdded = this.postJpaRepository.existsByText(postText);

        // Arrange
        assertThat(postWasAdded).isTrue();
    }

    @Test
    public void testIfOnlyVisibleThreadsCanBePaged() {
        // Arrange
        Topic topicWithTwoThreads =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(2L).get());

        Thread visibleThreadInTopicWithTwoThreads =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(1L).get());

        // Act
        ThreadPage loadedPage =
                this.threadRepositoryImpl.getThreadPageFromDbByVisibility(
                        topicWithTwoThreads.getId(), 0, true);

        List<Thread> loadedThreads = loadedPage.getThreads();

        // Assert
        assertThat(loadedThreads).containsExactly(visibleThreadInTopicWithTwoThreads);
    }

    @Test
    public void testIfOnlyNotVisibleThreadsCanBePaged() {
        // Arrange
        Topic topicWithTwoThreads =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(2L).get());

        Thread notVisibleThreadInTopicWithTwoThreads =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());

        // Act
        ThreadPage loadedPage =
                this.threadRepositoryImpl.getThreadPageFromDbByVisibility(
                        topicWithTwoThreads.getId(), 0, false);

        List<Thread> loadedThreads = loadedPage.getThreads();

        // Assert
        assertThat(loadedThreads).containsExactly(notVisibleThreadInTopicWithTwoThreads);
    }

    @Test
    public void testIfVisibilityCanBeSet() {
        Thread notVisibleThreadInTopicWithTwoThreads =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());

        // Act
        this.threadRepositoryImpl.setThreadVisible(
                notVisibleThreadInTopicWithTwoThreads.getId());

        // load updated Thread
        Thread updatedThread = ThreadMapper.mapThreadDtoToThread(
                this.threadJpaRepository.findById(
                        notVisibleThreadInTopicWithTwoThreads.getId().getId()).get());

        Boolean isVisible = updatedThread.getVisible();

        // Assert
        assertThat(isVisible).isTrue();
    }

    @Test
    public void testIfInvisibleThreadsAreCountedCorrectly() {
        // Arrange
        Topic topicWithTwoThreads =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(2L).get());

        // Act
        int notVisibleCount =
                this.threadRepositoryImpl.countInvisibleThreads(topicWithTwoThreads.getId());

        // Assert
        assertThat(notVisibleCount).isEqualTo(1);
    }

    @Test
    public void testIfThreadCanBeDeletedFromDatabase() {
        // Arrange
        Thread threadWithTwoPosts =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());

        // Act
        this.threadRepositoryImpl.deleteThread(threadWithTwoPosts.getId());

        Boolean wasDeleted =
                !this.threadJpaRepository.existsById(threadWithTwoPosts.getId().getId());

        // Assert
        assertThat(wasDeleted).isTrue();
    }
}
