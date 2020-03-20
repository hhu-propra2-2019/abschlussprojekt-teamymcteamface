package mops.foren;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ForumMapperTests {

    private final ForumDTO forumDTO;

    private final Forum forum;

    public ForumMapperTests() {
        this.forum = Forum.builder()
                .id(new ForumId(1L))
                .title("forum title")
                .description("forum description")
                .build();

        this.forumDTO = ForumDTO.builder()
                .id(1L)
                .title("forum title")
                .description("forum description")
                .build();
    }

    @Test
    public void testTitleIsCorrectlyMappedFormModelToDTO() {
        // Act
        String title = ForumMapper.mapForumToForumDTO(this.forum).getTitle();

        // Assert
        assertThat(title).isEqualTo(this.forum.getTitle());
    }

    @Test
    public void testDescriptionIsCorrectlyMappedFormModelToDTO() {
        // Act
        String description = ForumMapper.mapForumToForumDTO(this.forum).getDescription();

        // Assert
        assertThat(description).isEqualTo(this.forum.getDescription());
    }

    @Test
    public void testTitleIsCorrectlyMappedFormDTOToModel() {
        // Act
        String title = ForumMapper.mapForumDtoToForum(this.forumDTO).getTitle();

        // Assert
        assertThat(title).isEqualTo(this.forumDTO.getTitle());
    }

    @Test
    public void testDescriptionIsCorrectlyMappedFormDTOToModel() {
        // Act
        String description = ForumMapper.mapForumDtoToForum(this.forumDTO).getDescription();

        // Assert
        assertThat(description).isEqualTo(this.forumDTO.getDescription());
    }

    @Test
    public void testIdIsCorrectlyMappedFromDTOToModel() {
        // Act
        ForumId forumId = ForumMapper.mapForumDtoToForum(this.forumDTO).getId();

        // Assert
        assertThat(forumId.getId()).isEqualTo(this.forumDTO.getId());
    }
}