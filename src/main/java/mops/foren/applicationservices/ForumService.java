package mops.foren.applicationservices;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IForumRepository;

import java.util.List;

@ApplicationService
public class ForumService {

    private IForumRepository forumRepository;

    public ForumService(IForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Method to get all forums for a user.
     *
     * @param user The user
     * @return A list of forums.
     */
    public List<Forum> getForums(User user) {
        return forumRepository.getForumsFromDB(user);
    }

    public Forum getForum(ForumId forumId) { return forumRepository.getOneForumFromDB(forumId);}

}
