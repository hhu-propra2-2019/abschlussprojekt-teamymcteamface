package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ForumId;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TopicMapperTests {

    private TopicDTO topicDTO;

    /**
     * Sets up the test environment for TopicMapper.
     */
    @BeforeEach
    public void setUp() {
        ForumId forumId = new ForumId(2L);

        ForumDTO forumDTO = ForumDTO.builder() // Only Id is needed
                .id(forumId.getId())
                .build();

        this.topicDTO = TopicDTO.builder()
                .id(1L)
                .forum(forumDTO)
                .title("topic title")
                .description("topic description")
                .build();
    }

    @Test
    public void testTitleIsCorrectlyMappedFromTopicDTOToModel() {
        // Act
        String title = TopicMapper.mapTopicDtoToTopic(this.topicDTO).getTitle();

        // Assert
        assertThat(title).isEqualTo(this.topicDTO.getTitle());
    }

    @Test
    public void testDescriptionIsCorrectlyMappedFromTopicDTOToModel() {
        // Act
        String description = TopicMapper.mapTopicDtoToTopic(this.topicDTO).getDescription();

        // Assert
        assertThat(description).isEqualTo(this.topicDTO.getDescription());
    }

    @Test
    public void testIdIsCorrectlyMappedFromTopicDTOToModel() {
        // Act
        Long topicId = TopicMapper.mapTopicDtoToTopic(this.topicDTO).getId().getId();

        // Assert
        assertThat(topicId).isEqualTo(this.topicDTO.getId());
    }

    @Test
    public void testForumIdIsCorrectlyMappedFromTopicDTOToModel() {
        // Act
        ForumId forumId = TopicMapper.mapTopicDtoToTopic(this.topicDTO).getForumId();

        // Assert
        assertThat(forumId.getId()).isEqualTo(this.topicDTO.getForum().getId());
    }
}
