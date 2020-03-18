package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class UserMapper {
    /**
     * Maps a userDTO object to the resulting user object.
     *
     * @param userDTO  the userDTO object that should be mapped.
     * @param forenIds the List of ForenIDs for the userDTO.
     * @return the resulting user.
     */
    public static User mapUserDtoToUser(Optional<UserDTO> userDTO, List<ForumId> forenIds) {
        return User.builder()
                .name(userDTO.get().getUsername())
                .email(userDTO.get().getEmail())
                .forums(forenIds)
                .build();
    }

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
                .build();
    }

    public static User mapUserDtoToUser(UserDTO userDTO) {
        List<ForumId> forenIds = getForumIdFromUserDto(userDTO);
        return User.builder()
                .name(userDTO.getUsername())
                .email(userDTO.getEmail())
                .forums(forenIds)
                .build();
    }

    private static List<ForumId> getForumIdFromUserDto(UserDTO userDTO) {
        return userDTO.getForums().stream()
                .map(ForumMapper::mapForumDtoToForum)
                .map(forum -> new ForumId(forum.getId().getId()))
                .collect(Collectors.toList());
    }
}
