package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.ForumJpaRepository;
import mops.foren.infrastructure.persistence.repositories.TopicJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
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

    private final IForumRepository forumRepositoryImpl;
    private final UserJpaRepository userJpaRepository;
    private final ForumJpaRepository forumJpaRepository;
    private final TopicJpaRepository topicJpaRepository;

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
                                    IForumRepository forumRepositoryImpl,
                                    ForumJpaRepository forumJpaRepository,
                                    TopicJpaRepository topicJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.forumRepositoryImpl = forumRepositoryImpl;
        this.forumJpaRepository = forumJpaRepository;
        this.topicJpaRepository = topicJpaRepository;
    }

    @Test
    public void testIfForumsForUserCanBeLoadedFromDatabase() {
        // Arrange
        User userWithTwoForums =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user3").get());

        Forum firstForumForUser =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(2L).get());

        Forum secondForumForUser =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(3L).get());

        List<Forum> expectedForum =
                Arrays.asList(firstForumForUser, secondForumForUser);

        // Act
        List<Forum> loadedForums =
                this.forumRepositoryImpl.getForumsFromDB(userWithTwoForums);

        // Assert
        assertThat(loadedForums).containsOnlyElementsOf(expectedForum);
    }

    @Test
    public void testIfOneForumCanBeLoadedFromDatabase() {
        // Arrange
        Forum forumInDatabase =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(1L).get());

        // Act
        Forum loadedForum = this.forumRepositoryImpl.getOneForumFromDB(
                forumInDatabase.getId());

        // Assert
        assertThat(loadedForum).isEqualTo(forumInDatabase);
    }

    @Test
    public void testIfTopicCanBeAddedToForum() {
        // Arrange
        Forum forumWithoutTopics =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(1L).get());

        Topic newTopic = Topic.builder()
                .title("new topic title")
                .description("new topic description")
                .forumId(forumWithoutTopics.getId())
                .anonymous(false)
                .moderated(false)
                .build();

        // Act
        this.forumRepositoryImpl.addTopicInForum(forumWithoutTopics.getId(), newTopic);
        Boolean topicWasAdded =
                this.topicJpaRepository.existsByForum_Id(forumWithoutTopics.getId().getId());

        // Assert
        assertThat(topicWasAdded).isTrue();
    }
}
