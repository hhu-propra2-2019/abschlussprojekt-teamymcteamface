package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;

public interface IUserRepository {
    boolean findUser(String name);

    void addNewUser(String name);

    User getUser(String name);

    void updateUser(User user, Forum forum);
}
