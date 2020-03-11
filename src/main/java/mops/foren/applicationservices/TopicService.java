package mops.foren.applicationservices;

import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.ITopicRepository;

import java.util.List;

public class TopicService {

    private ITopicRepository topicRepository;

    public TopicService(ITopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getTopics(long forumId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method to add a topic.
     *
     * @param topic   The thread to add
     * @param user    The user that wants to create the topic
     * @param forumId The forumId the topic belongs to
     */
    public void addTopic(Topic topic, User user, long forumId) {
        if (user.checkPermission(forumId)) {
            // ADD
        }
    }
}
