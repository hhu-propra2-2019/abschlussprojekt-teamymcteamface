package mops.foren.applicationservices;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;
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

    public void addThreadInTopic(TopicId topicId, Thread thread) {
        this.topicRepository.addThreadInTopic(topicId, thread);
    }

    public void deleteTopic(TopicId topicId) {
        this.topicRepository.deleteTopic(topicId);
    }
}
