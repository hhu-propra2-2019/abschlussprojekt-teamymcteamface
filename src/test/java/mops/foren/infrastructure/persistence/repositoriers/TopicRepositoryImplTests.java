package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.ITopicRepository;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.ForumJpaRepository;
import mops.foren.infrastructure.persistence.repositories.ThreadJpaRepository;
import mops.foren.infrastructure.persistence.repositories.TopicJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
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

    private final ITopicRepository topicRepositoryImpl;
    private final ThreadJpaRepository threadJpaRepository;
    private final ForumJpaRepository forumJpaRepository;
    private final TopicJpaRepository topicJpaRepository;
    private final UserJpaRepository userJpaRepository;

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
                                    ITopicRepository topicRepositoryImpl,
                                    ForumJpaRepository forumJpaRepository,
                                    TopicJpaRepository topicJpaRepository,
                                    UserJpaRepository userJpaRepository) {
        this.threadJpaRepository = threadJpaRepository;
        this.topicRepositoryImpl = topicRepositoryImpl;
        this.forumJpaRepository = forumJpaRepository;
        this.topicJpaRepository = topicJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Test
    public void testIfTopicsCanBeLoadedForForumFromDatabase() {
        // Arrange
        Forum forumWithTwoTopics =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(2L).get());

        Topic firstTopicForForum =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(1L).get());

        Topic secondTopicForForum =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(2L).get());

        List<Topic> expectedTopics =
                Arrays.asList(firstTopicForForum, secondTopicForForum);

        // Act
        List<Topic> loadedTopics =
                this.topicRepositoryImpl.getTopicsFromDB(forumWithTwoTopics.getId());

        // Assert
        assertThat(loadedTopics).containsOnlyElementsOf(expectedTopics);
    }

    @Test
    public void testIfOneTopicCanBeLoadedFromDatabase() {
        // Arrange
        Topic topicInDatabase =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(1L).get());

        // Act
        Topic loadedTopic =
                this.topicRepositoryImpl.getOneTopicFromDB(topicInDatabase.getId());

        // Assert
        assertThat(loadedTopic).isEqualTo(topicInDatabase);
    }

    @Test
    public void testIfTopicCanBeDeleted() {
        // Arrange
        Topic topicInDatabase =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(1L).get());
        // Act
        this.topicRepositoryImpl.deleteTopic(topicInDatabase.getId());

        Boolean topicDoesExist =
                this.topicJpaRepository.existsById(topicInDatabase.getId().getId());

        // Assert
        assertThat(topicDoesExist).isFalse();
    }

    @Test
    public void testIfThreadCanBeAddedToTopic() {
        // Arrange
        User userInDatabase =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user2").get());

        Topic topicWithoutThread =
                TopicMapper.mapTopicDtoToTopic(this.topicJpaRepository.findById(1L).get());

        Thread newThread = Thread.builder()
                .title("new Thread title")
                .description("new Thread description")
                .author(userInDatabase)
                .moderated(false)
                .anonymous(false)
                .visible(false)
                .forumId(topicWithoutThread.getForumId())
                .topicId(topicWithoutThread.getId())
                .lastPostTime(LocalDateTime.now())
                .build();

        // Act
        this.topicRepositoryImpl.addThreadInTopic(topicWithoutThread.getId(), newThread);

        Boolean threadWasAdded =
                this.threadJpaRepository.existsByTopic_Id(topicWithoutThread.getId().getId());

        // Assert
        assertThat(threadWasAdded).isTrue();
    }
}
