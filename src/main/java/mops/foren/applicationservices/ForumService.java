package mops.foren.applicationservices;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.domain.services.ForumModelService;

import java.util.List;

@ApplicationService
public class ForumService {

    private IForumRepository forumRepository;
    private ForumModelService forumModelService;

    public ForumService(IForumRepository forumRepository, ForumModelService forumModelService) {
        this.forumRepository = forumRepository;
        this.forumModelService = forumModelService;
    }

    /**
     * Method to get all forums for a user.
     *
     * @param user The user
     * @return A list of forums.
     */
    public List<Forum> getForums(User user) {
        return this.forumRepository.getForumsFromDB(user);
    }

    public Forum getForum(ForumId forumId) {
        return this.forumRepository.getOneForumFromDB(forumId);
    }

    public void addTopicInForum(ForumId forumId, Topic topic) {
        this.forumRepository.addTopicInForum(forumId, topic);
    }

    public void updateLastTimeChanged(Forum forum) {
        this.forumModelService.updateLastChangedTime(forum);
    }

}
