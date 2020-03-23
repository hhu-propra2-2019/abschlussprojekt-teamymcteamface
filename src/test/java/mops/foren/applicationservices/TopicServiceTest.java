package mops.foren.applicationservices;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.repositoryabstraction.ITopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TopicServiceTest {

    private ITopicRepository topicRepository;
    private TopicService topicService;

    @BeforeEach
    public void setUp() {
        this.topicRepository = mock(ITopicRepository.class);
        this.topicService = new TopicService(this.topicRepository);
    }


    @Test
    public void testGetTopicsDirectsTheCallCorrectly() {
        // Arrange
        ForumId forumId = new ForumId(0L);

        // Act
        topicService.getTopics(forumId);

        //Assert
        verify(topicRepository).getTopicsFromDB(forumId);
    }

    @Test
    public void testAddThreadInTopicDirectsTheCallCorrectly() {
        // Arrange
        TopicId topicId = new TopicId(0L);
        Thread thread = Thread.builder().build();

        // Act
        topicService.addThreadInTopic(topicId, thread);

        //Assert
        verify(topicRepository).addThreadInTopic(topicId, thread);
    }

}
