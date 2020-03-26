package mops.foren.applicationservices;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.domain.services.ForumModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ForumServiceTest {
    private IForumRepository forumRepository;
    private ForumService forumService;
    private ForumModelService forumModelService;

    @BeforeEach
    public void setUp() {
        this.forumRepository = mock(IForumRepository.class);
        this.forumModelService = mock(ForumModelService.class);
        this.forumService = new ForumService(this.forumRepository, this.forumModelService);
    }

    @Test
    public void testGetForumsDirectsTheCallCorrectly() {
        // Arrange
        User testUser = User.builder()
                .build();

        // Act
        this.forumService.getForums(testUser);

        //Assert
        verify(this.forumRepository).getForumsFromDB(testUser);
    }

    @Test
    public void testGetForumDirectsTheCallCorrectly() {
        // Arrange
        ForumId forumId = new ForumId(0L);

        // Act
        this.forumService.getForum(forumId);

        //Assert
        verify(this.forumRepository).getOneForumFromDB(forumId);
    }

    @Test
    public void testAddTopicInForumDirectsTheCallCorrectly() {
        // Arrange
        ForumId forumId = new ForumId(0L);
        Topic topic = Topic.builder()
                .build();

        // Act
        this.forumService.addTopicInForum(forumId, topic);

        //Assert
        verify(this.forumRepository).addTopicInForum(forumId, topic);
    }
}
