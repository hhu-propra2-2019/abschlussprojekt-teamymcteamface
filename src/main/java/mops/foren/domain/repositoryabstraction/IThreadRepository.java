package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.ThreadPage;

import java.util.List;

public interface IThreadRepository {
    ThreadPage getThreadPageFromDB(TopicId topicId, Integer page);

    Thread getThreadById(ThreadId threadId);

    void addPostInThread(ThreadId threadId, Post post);

    ThreadPage getThreadPageFromDbByVisibility(TopicId topicId, Integer page, Boolean visibility);

    void setThreadVisible(ThreadId threadId);

    Integer countInvisibleThreads(TopicId topicId);

    void deleteThread(ThreadId threadId);

    List<Thread> getThreadByForumId(ForumId forumId);
}
