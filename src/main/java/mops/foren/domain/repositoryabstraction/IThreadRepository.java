package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.Topic;

import java.util.List;

public interface IThreadRepository {
    List<Thread> getThreadsFromTopic(Topic topic);

    Thread getThreadById(ThreadId threadId);
}
