package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PostMapperTests {

    private PostDTO postDTO;

    /**
     * Sets up a new test environment for PostMapper.
     */
    @BeforeEach
    public void setUp() {
        ThreadId threadId = new ThreadId(2L);

        ThreadDTO threadDTO = ThreadDTO.builder() // only Id is needed
                .id(threadId.getId())
                .build();

        Set<ForumDTO> forumDTOs = Stream.of(1L, 2L, 3L)
                .map(id -> ForumDTO.builder()
                        .id(id)
                        .title("title for " + id)
                        .description("description for " + id)
                        .build()
                )
                .collect(Collectors.toSet());

        UserDTO userDTO = UserDTO.builder()
                .username("user")
                .email("user@hhu.de")
                .forums(forumDTOs)
                .build();

        ForumDTO forumDTO = ForumDTO.builder()
                .id(10L)
                .build();

        this.postDTO = PostDTO.builder()
                .id(1L)
                .thread(threadDTO)
                .dateTime(LocalDateTime.now())
                .author(userDTO)
                .forum(forumDTO)
                .text("text")
                .anonymous(true)
                .visible(false)
                .build();
    }

    @Test
    public void testIdIsCorrectlyMappedFromPostDTOToModel() {
        // Act
        Long id = PostMapper.mapPostDtoToPost(this.postDTO).getId().getId();

        // Assert
        assertThat(id).isEqualTo(this.postDTO.getId());
    }

    @Test
    public void testThreadIdIsCorrectlyMappedFromPostDTOToModel() {
        // Act
        ThreadId threadId = PostMapper.mapPostDtoToPost(this.postDTO).getThreadId();

        // Assert
        assertThat(threadId.getId()).isEqualTo(this.postDTO.getThread().getId());
    }

    @Test
    public void testAuthorIsCorrectlyMappedFromPostDTOToModel() {
        // Act
        User user = PostMapper.mapPostDtoToPost(this.postDTO).getAuthor();

        // Assert
        Assertions.assertThat(UserMapper.mapUserToUserDto(user))
                .isEqualTo(this.postDTO.getAuthor());
    }

    @Test
    public void testTextIsCorrectlyMappedFromPostDTOToModel() {
        // Act
        String text = PostMapper.mapPostDtoToPost(this.postDTO).getText();

        // Assert
        assertThat(text).isEqualTo(this.postDTO.getText());
    }

    @Test
    public void testAnonymIsCorrectlyMappedFromPostDTOToModel() {
        // Act
        Boolean anonymous = PostMapper.mapPostDtoToPost(this.postDTO).getAnonymous();

        // Assert
        assertThat(anonymous).isTrue();
    }

    @Test
    public void testVisibleIsCorrectlyMappedFromPostDTOToModel() {
        // Act
        Boolean visible = PostMapper.mapPostDtoToPost(this.postDTO).getVisible();

        // Assert
        assertThat(visible).isFalse();
    }

    @Test
    public void testForumIdIsCorrectlyMappedFromPostDTOToModel() {
        // Act
        ForumId forumId = PostMapper.mapPostDtoToPost(this.postDTO).getForumId();

        // Assert
        assertThat(forumId.getId()).isEqualTo(10L);
    }
}
