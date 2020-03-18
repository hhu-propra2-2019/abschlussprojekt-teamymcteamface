package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;

import java.util.List;

public interface ITopicRepository {

    List<Topic> getTopicsFromDB(ForumId forumId);

    Topic getOneTopicFromDB(TopicId topicId);
}
