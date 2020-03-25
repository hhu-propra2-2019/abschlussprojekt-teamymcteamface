package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.paging.ThreadPage;

public interface IThreadRepository {
    ThreadPage getThreadPageFromDB(TopicId topicId, Integer page);

    Thread getThreadById(ThreadId threadId);

    void addPostInThread(ThreadId threadId, Post post);

    ThreadPage getThreadPageFromDbByVisibility(TopicId topicId, int page, boolean visibility);

    void setThreadVisible(ThreadId threadId);

    int countInvisibleThreads(TopicId topicId);
}
