package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;

import java.util.List;

public interface IThreadRepository {
    List<Thread> getThreadsFromDB(TopicId topicId);

    Thread getThreadById(ThreadId threadId);

    void addPostInThread(ThreadId threadId, Post post);
}
