package mops.foren.applicationservices;

import mops.foren.domain.repositoryabstraction.IUserRepository;

public class UserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
