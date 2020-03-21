package mops.foren;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserMapperTests {

    private UserDTO userDTO;

    private User user;

    private List<ForumId> forumIds;

    /**
     * Sets up a new test environment for UserMapper.
     */
    @BeforeEach
    public void setUp() {
        this.user = User.builder()
                .name("user1")
                .email("user1@hhu.de")
                .build();

        this.forumIds = Stream.of(1L, 2L, 3L)
                .map(ForumId::new)
                .collect(Collectors.toList());

        Set<ForumDTO> forumDTOs = this.forumIds.stream()
                .map(id -> ForumDTO.builder()
                        .id(id.getId())
                        .title("title for " + id.getId())
                        .description("description for " + id.getId())
                        .build()
                )
                .collect(Collectors.toSet());

        this.userDTO = UserDTO.builder()
                .username("user1")
                .email("user1@hhu.de")
                .forums(forumDTOs)
                .build();
    }

    @Test
    public void testNameIsCorrectlyMappedFormModelToDTO() {
        // Act
        String username = UserMapper.mapUserToUserDto(this.user).getUsername();

        // Assert
        assertThat(username).isEqualTo(this.user.getName());
    }

    @Test
    public void testEmailIsCorrectlyMappedFormModelToDTO() {
        // Act
        String email = UserMapper.mapUserToUserDto(this.user).getEmail();

        // Assert
        assertThat(email).isEqualTo(this.user.getEmail());
    }

    @Test
    public void testNameIsCorrectlyMappedFormDTOToModel() {
        // Act
        String username = UserMapper.mapUserDtoToUser(this.userDTO).getName();

        // Assert
        assertThat(username).isEqualTo(this.userDTO.getUsername());
    }

    @Test
    public void testEmailIsCorrectlyMappedFormDTOToModel() {
        // Act
        String email = UserMapper.mapUserDtoToUser(this.userDTO).getEmail();

        // Assert
        assertThat(email).isEqualTo(this.userDTO.getEmail());
    }

    @Test
    public void testForumsAreCorrectlyMappedFromDTOToModel() {
        // Act
        List<ForumId> forumIds = UserMapper.mapUserDtoToUser(this.userDTO).getForums();

        // Assert
        assertThat(forumIds).isEqualTo(this.forumIds);
    }
}
