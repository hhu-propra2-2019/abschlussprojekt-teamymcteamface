package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;

public interface IUserRepository {

    void addNewUserToDB(User user);

    User getUserFromDB(User user);

    void addForumToUser(User user, Forum forum);

    Boolean isUserNotInDB(User user);
}
