package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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

        this.postDTO = PostDTO.builder()
                .id(1L)
                .thread(threadDTO)
                .dateTime(LocalDateTime.now())
                .author(userDTO)
                .text("text")
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
}
