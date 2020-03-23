package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.PermissionManager;
import mops.foren.domain.model.Role;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    /**
     * Maps a User object to the resulting UserDTO object.
     *
     * @param user the user object that should be mapped.
     * @return the resulting UserDTO object.
     */
    public static UserDTO mapUserToUserDto(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .roles(getRolesMap(user.getPermissionManager()))
                .build();
    }

    private static Map<Long, Role> getRolesMap(PermissionManager permissionManager) {
        Map<Long, Role> roles = new HashMap<>();
        permissionManager.getAdmin().stream()
                .map(forumId -> roles.put(forumId.getId(), Role.ADMIN));

        permissionManager.getModerator().stream()
                .map(forumId -> roles.put(forumId.getId(), Role.MODERATOR));

        return roles;
    }

    /**
     * Maps a userDTO object to the resulting user object.
     *
     * @param userDTO the userDTO object that should be mapped.
     * @return the resulting user.
     */
    public static User mapUserDtoToUser(UserDTO userDTO) {
        List<ForumId> forumIds = getForumIdFromUserDto(userDTO);

        PermissionManager permissionManager = new PermissionManager();

        userDTO.getRoles().forEach((forumId, role) -> {
            permissionManager.addForumWithPermission(forumId, role);
        });

        for (ForumId forumId : forumIds) {
            permissionManager.addForumWithPermission(forumId.getId(), Role.STUDENT);
        }

        return User.builder()
                .name(userDTO.getUsername())
                .email(userDTO.getEmail())
                .permissionManager(permissionManager)
                .forums(forumIds)
                .build();
    }


    private static List<ForumId> getForumIdFromUserDto(UserDTO userDTO) {
        return userDTO.getForums().stream()
                .map(ForumMapper::mapForumDtoToForum)
                .map(forum -> new ForumId(forum.getId().getId()))
                .collect(Collectors.toList());
    }
}
