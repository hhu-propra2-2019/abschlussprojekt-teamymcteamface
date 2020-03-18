package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;

import java.util.List;

public interface IThreadRepository {
    List<Thread> getThreadsFromTopic(Topic topic);

    List<Thread> getThreadsFromDB(TopicId topicId);

    Thread getThreadById(ThreadId threadId);
}
