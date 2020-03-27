package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TopicRepositoryImplTests {

    /**
     * Repository under test.
     */
    private final TopicRepositoryImpl topicRepositoryImpl;

    /**
     * Jpa thread repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final ThreadJpaRepository threadJpaRepository;

    /**
     * Jpa forum repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final ForumJpaRepository forumJpaRepository;

    /**
     * Jpa topic repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final TopicJpaRepository topicJpaRepository;

    /**
     * Jpa user repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final UserJpaRepository userJpaRepository;

    private Forum forumWithTwoTopics;
    private Topic firstTopicForForum;
    private Topic secondTopicForForum;
    private Topic topicWithoutThread;
    private User userInDatabase;

    /**
     * Constructor for repo injection.
     *
     * @param threadJpaRepository injected threadJpaRepository
     * @param topicRepositoryImpl injected topicRepositoryImpl
     * @param forumJpaRepository  injected forumJpaRepository
     * @param topicJpaRepository  injected topicJpaRepository
     * @param userJpaRepository   injected userJpaRepository
     */
    @Autowired
    public TopicRepositoryImplTests(ThreadJpaRepository threadJpaRepository,
                                    TopicRepositoryImpl topicRepositoryImpl,
                                    ForumJpaRepository forumJpaRepository,
                                    TopicJpaRepository topicJpaRepository,
                                    UserJpaRepository userJpaRepository) {
        this.threadJpaRepository = threadJpaRepository;
        this.topicRepositoryImpl = topicRepositoryImpl;
        this.forumJpaRepository = forumJpaRepository;
        this.topicJpaRepository = topicJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    /**
     * Set up method with injection. SetsUp the tests.
     */
    @BeforeEach
    public void setUp() {
        this.forumWithTwoTopics =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(2L).get());

        this.firstTopicForForum =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(1L).get());

        this.secondTopicForForum =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(2L).get());

        this.topicWithoutThread =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(1L).get());

        this.userInDatabase =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user2").get());
    }

    @Test
    public void testIfTopicsCanBeLoadedForForumFromDatabase() {
        // Arrange
        List<Topic> expectedTopics =
                Arrays.asList(this.firstTopicForForum, this.secondTopicForForum);

        // Act
        List<Topic> loadedTopics =
                this.topicRepositoryImpl.getTopicsFromDB(this.forumWithTwoTopics.getId());

        // Assert
        assertThat(loadedTopics).containsOnlyElementsOf(expectedTopics);
    }

    @Test
    public void testIfOneTopicCanBeLoadedFromDatabase() {
        // Act
        Topic loadedTopic =
                this.topicRepositoryImpl.getOneTopicFromDB(this.firstTopicForForum.getId());

        // Assert
        assertThat(loadedTopic).isEqualTo(this.firstTopicForForum);
    }

    @Test
    public void testIfTopicCanBeDeleted() {
        // Act
        this.topicRepositoryImpl.deleteTopic(this.topicWithoutThread.getId());

        Boolean topicDoesExist =
                this.topicJpaRepository.existsById(this.topicWithoutThread.getId().getId());

        // Assert
        assertThat(topicDoesExist).isFalse();
    }

    @Test
    public void testIfThreadCanBeAddedToTopic() {
        // Arrange
        Thread newThread = Thread.builder()
                .title("new Thread title")
                .description("new Thread description")
                .author(this.userInDatabase)
                .moderated(false)
                .anonymous(false)
                .visible(false)
                .forumId(this.topicWithoutThread.getForumId())
                .topicId(this.topicWithoutThread.getId())
                .lastPostTime(LocalDateTime.now())
                .build();

        // Act
        this.topicRepositoryImpl.addThreadInTopic(this.topicWithoutThread.getId(), newThread);

        Boolean threadWasAdded =
                this.threadJpaRepository.existsByTopic_Id(this.topicWithoutThread.getId().getId());

        // Assert
        assertThat(threadWasAdded).isTrue();
    }
}
