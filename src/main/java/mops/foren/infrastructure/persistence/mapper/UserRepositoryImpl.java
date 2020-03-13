package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IUserRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.repositories.ForumJPARepository;
import mops.foren.infrastructure.persistence.repositories.UserJPARepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {


    UserJPARepository userRepository;
    ForumJPARepository forumRepository;

    public UserRepositoryImpl(UserJPARepository userRepository, ForumJPARepository forumRepository) {
        this.userRepository = userRepository;
        this.forumRepository = forumRepository;
    }

    @Override
    public boolean isUserExistent(User user) {
        return userRepository.findById(user.getName()).isPresent();
    }

    @Override
    public void addNewUser(User user) {
        UserDTO userDTO = mapUserToUserDTO(user);
        userRepository.save(userDTO);
    }

    private UserDTO mapUserToUserDTO(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User getUser(User user) {
        Optional<UserDTO> userDTO = userRepository.findById(user.getName());
        List<Long> forenIds = getForenIdsFromUser(userDTO);
        User userFromDB = mapUserDTOToUser(userDTO, forenIds);
        return userFromDB;
    }

    private User mapUserDTOToUser(Optional<UserDTO> userDTO, List<Long> forenIds) {
        return User.builder()
                .name(userDTO.get().getUsername())
                .email(userDTO.get().getEmail())
                .forums(forenIds)
                .build();
    }

    private List<Long> getForenIdsFromUser(Optional<UserDTO> userDTO) {
        if (userDTO.get().getForums() == null) {
            return new LinkedList<>();
        }

        return userDTO.get().getForums().stream()
                .map(ForumDTO::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(User user, Forum forum) {
        UserDTO userDTO = userRepository.findById(user.getName()).get();
        ForumDTO forumDTO = mapForumToForumDTO(forum);
        userDTO.getForums().add(forumDTO);
        userRepository.save(userDTO);
    }

    private ForumDTO mapForumToForumDTO(Forum forum) {
        return ForumDTO.builder()
                .title(forum.getTitle())
                .description(forum.getDescription())
                .build();
    }


}
