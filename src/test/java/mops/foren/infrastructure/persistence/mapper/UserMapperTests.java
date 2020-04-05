package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.PermissionManager;
import mops.foren.domain.model.Role;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTests {

    private UserDTO userDTO;

    private User user;

    private List<ForumId> forumIds;

    /**
     * Sets up a new test environment for UserMapper.
     */
    @BeforeEach
    public void setUp() {
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.addForumWithPermission(4L, Role.MODERATOR);
        permissionManager.addForumWithPermission(5L, Role.MODERATOR);
        permissionManager.addForumWithPermission(6L, Role.ADMIN);
        permissionManager.addForumWithPermission(7L, Role.STUDENT);

        this.user = User.builder()
                .name("user1")
                .email("user1@hhu.de")
                .permissionManager(permissionManager)
                .build();

        this.forumIds = Stream.of(1L, 2L, 3L)
                .map(ForumId::new)
                .collect(Collectors.toList());

        final Set<ForumDTO> forumDTOs = this.forumIds.stream()
                .map(id -> ForumDTO.builder()
                        .id(id.getId())
                        .title("title for " + id.getId())
                        .description("description for " + id.getId())
                        .build()
                )
                .collect(Collectors.toSet());

        Map<Long, Role> rolesHashmap = new HashMap<>();
        rolesHashmap.put(4L, Role.MODERATOR);
        rolesHashmap.put(5L, Role.MODERATOR);
        rolesHashmap.put(6L, Role.ADMIN);
        this.userDTO = UserDTO.builder()
                .username("user1")
                .email("user1@hhu.de")
                .forums(forumDTOs)
                .roles(rolesHashmap)
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
    public void testPermissionManagerIsCorrectlyMappedFormModelToDTO() {
        // Act
        Map<Long, Role> roleMap = UserMapper.mapUserToUserDto(this.user).getRoles();

        // Assert (Student is missing on purpose!)
        assertThat(roleMap).contains(entry(4L, Role.MODERATOR), entry(5L, Role.MODERATOR),
                entry(6L, Role.ADMIN));
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

    @Test
    public void testPermissionManagerIsCorrectlyMappedFromDTOToModel() {
        // Act
        PermissionManager pm = UserMapper.mapUserDtoToUser(this.userDTO).getPermissionManager();

        // Assert
        assertThat(pm.getAdmin()).containsExactly(new ForumId(6L));
        assertThat(pm.getModerator()).containsExactly(new ForumId(5L), new ForumId(4L));
        assertThat(pm.getStudent())
                .containsExactly(new ForumId(1L), new ForumId(2L), new ForumId(3L));
    }
}
