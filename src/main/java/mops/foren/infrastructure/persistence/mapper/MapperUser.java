package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IUserRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.repositorys.ForumDtoRepository;
import mops.foren.infrastructure.persistence.repositorys.UserDtoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MapperUser implements IUserRepository {


    UserDtoRepository userRepository;
    ForumDtoRepository forumRepository;

    public MapperUser(UserDtoRepository userRepository, ForumDtoRepository forumRepository) {
        this.userRepository = userRepository;
        this.forumRepository = forumRepository;
    }

    @Override
    public boolean findUser(String name) {
        return userRepository.findById(name).isPresent();
    }

    @Override
    public void addNewUser(String name) {
        UserDTO newUser = UserDTO.builder().username(name).build();
        userRepository.save(newUser);
    }

    @Override
    public User getUser(String name) {
        Optional<UserDTO> userDTO = userRepository.findById(name);
        List<Long> forenIds = new ArrayList<>();
        if (userDTO.get().getForums() != null) {
            forenIds = userDTO.get().getForums().stream()
                    .map(ForumDTO::getId)
                    .collect(Collectors.toList());
        }
        User user = User.builder().name(userDTO.get().getUsername())
                .forums(forenIds).build();
        return user;
    }

    @Override
    public void updateUser(User user, Forum forum) {
        List<ForumDTO> foren = new ArrayList<>();
        if (user.hasForen()) {
            foren = forumRepository.findAllById(user.getForums());
        }
        ForumDTO forumDTO = ForumDTO.builder().title(forum.getTitle())
                .description(forum.getDescription())
                .build();
        foren.add(forumDTO);
        UserDTO userDTO = UserDTO.builder().username(user.getName())
                .forums(foren)
                .build();
        userRepository.save(userDTO);
    }
}
