package mops.foren.applicationservices;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Permission;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.ITopicRepository;

import java.util.List;

@ApplicationService
public class TopicService {

    private ITopicRepository topicRepository;

    public TopicService(ITopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getTopics(ForumId forumId) {
        return this.topicRepository.getTopicsFromDB(forumId);
    }

    /**
     * Method to add a topic.
     *
     * @param topic   The thread to add
     * @param user    The user that wants to create the topic
     * @param forumId The forumId the topic belongs to
     */
    public void addTopic(Topic topic, User user, ForumId forumId) {
        if (user.checkPermission(forumId, Permission.CREATE_TOPIC)) {
            // ADD
        }
    }
}
