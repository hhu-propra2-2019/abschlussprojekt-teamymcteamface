package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;

public interface IUserRepository {

    void addNewUser(User user);

    User getUser(User user);

    void updateUser(User user, Forum forum);

    boolean isUserExistent(User user);
}
