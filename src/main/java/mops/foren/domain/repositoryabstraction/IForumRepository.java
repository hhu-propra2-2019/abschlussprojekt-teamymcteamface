package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;

import java.util.List;

public interface IForumRepository {
    List<Forum> getForumsFromDB(User user);

    Forum getOneForumFromDB(ForumId forumId);
}
