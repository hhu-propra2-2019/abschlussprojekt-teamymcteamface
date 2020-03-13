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

    public boolean checkIfUserIsExistent(String name) {
        return userRepository.findUser(name);
    }

    public void addNewUser(String name) {
        userRepository.addNewUser(name);

    }

    public User getUser(String name) {
        return userRepository.getUser(name);
    }

    public void addForum(User user, Forum forum) {
        userRepository.updateUser(user, forum);
    }
}
