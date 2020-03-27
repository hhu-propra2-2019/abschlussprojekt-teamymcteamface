package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.ForumJpaRepository;
import mops.foren.infrastructure.persistence.repositories.ForumRepositoryImpl;
import mops.foren.infrastructure.persistence.repositories.TopicJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ForumRepositoryImplTests {

    /**
     * Repository under test.
     */
    private final ForumRepositoryImpl forumRepositoryImpl;

    /**
     * Jpa user repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final UserJpaRepository userJpaRepository;

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

    private User userWithTwoForums;
    private Forum forumWithoutTopics;
    private Forum firstForumForUser;
    private Forum secondForumForUser;

    /**
     * Constructor for repo injection.
     *
     * @param userJpaRepository   injected userJpaRepository
     * @param forumRepositoryImpl injected forumRepositoryImpl
     * @param forumJpaRepository  injected forumJpaRepository
     * @param topicJpaRepository  injected topicJpaRepository
     */
    @Autowired
    public ForumRepositoryImplTests(UserJpaRepository userJpaRepository,
                                    ForumRepositoryImpl forumRepositoryImpl,
                                    ForumJpaRepository forumJpaRepository,
                                    TopicJpaRepository topicJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.forumRepositoryImpl = forumRepositoryImpl;
        this.forumJpaRepository = forumJpaRepository;
        this.topicJpaRepository = topicJpaRepository;
    }

    /**
     * Set up method with injection. SetsUp the tests.
     */
    @BeforeEach
    public void setUp() {
        this.userWithTwoForums =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user3").get());

        this.firstForumForUser =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(2L).get());

        this.secondForumForUser =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(3L).get());

        this.forumWithoutTopics =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(1L).get());
    }

    @Test
    public void testIfForumsForUserCanBeLoadedFromDatabase() {
        // Arrange
        List<Forum> expectedForum =
                Arrays.asList(this.firstForumForUser, this.secondForumForUser);

        // Act
        List<Forum> loadedForums =
                this.forumRepositoryImpl.getForumsFromDB(this.userWithTwoForums);

        //Assert
        assertThat(loadedForums).containsOnlyElementsOf(expectedForum);
    }

    @Test
    public void testIfOneForumCanBeLoadedFromDatabase() {
        // Act
        Forum loadedForum = this.forumRepositoryImpl.getOneForumFromDB(
                this.forumWithoutTopics.getId());

        // Assert
        assertThat(loadedForum).isEqualTo(this.forumWithoutTopics);
    }

    @Test
    public void testIfTopicCanBeAddedToForum() {
        // Arrange
        Topic newTopic = Topic.builder()
                .title("new topic title")
                .description("new topic description")
                .forumId(this.forumWithoutTopics.getId())
                .anonymous(false)
                .moderated(false)
                .build();

        // Act
        this.forumRepositoryImpl.addTopicInForum(this.forumWithoutTopics.getId(), newTopic);
        Boolean topicWasAdded =
                this.topicJpaRepository.existsByForum_Id(this.forumWithoutTopics.getId().getId());

        // Assert
        assertThat(topicWasAdded).isTrue();
    }
}
