package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IUserRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {


    UserJpaRepository userRepository;
    ForumJpaRepository forumRepository;

    @SuppressWarnings("LineLength")
    public UserRepositoryImpl(UserJpaRepository userRepository, ForumJpaRepository forumRepository) {
        this.userRepository = userRepository;
        this.forumRepository = forumRepository;
    }

    @Override
    public boolean isUserExistent(User user) {
        return userRepository.findById(user.getName()).isPresent();
    }

    @Override
    public void addNewUser(User user) {
        UserDTO userDTO = UserMapper.mapUserToUserDto(user);
        userRepository.save(userDTO);
    }


    @Override
    public User getUser(User user) {
        Optional<UserDTO> userDTO = userRepository.findById(user.getName());
        List<ForumId> forenIds = getForenIdsFromUser(userDTO);
        return UserMapper.mapUserDtoToUser(userDTO, forenIds);
    }


    private List<ForumId> getForenIdsFromUser(Optional<UserDTO> userDTO) {
        if (userDTO.get().getForums() == null) {
            return new LinkedList<>();
        }

        return userDTO.get().getForums().stream()
                .map(ForumDTO::getId)
                .map(ForumId::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(User user, Forum forum) {
        UserDTO userDTO = userRepository.findById(user.getName()).get();
        ForumDTO forumDTO = ForumMapper.mapForumToForumDTO(forum);
        userDTO.getForums().add(forumDTO);
        userRepository.save(userDTO);
    }


}
