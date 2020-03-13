package mops.foren.applicationservices;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IUserRepository;

@ApplicationService
public class UserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkIfUserIsExistent(User user) {
        return userRepository.isUserExistent(user);
    }

    public void addNewUser(User user) {
        userRepository.addNewUser(user);

    }

    public User getUser(User user) {
        return userRepository.getUser(user);
    }

    public void addForum(User user, Forum forum) {
        userRepository.updateUser(user, forum);
    }
}
