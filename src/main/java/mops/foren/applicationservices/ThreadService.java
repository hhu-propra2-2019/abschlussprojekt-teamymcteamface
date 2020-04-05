package mops.foren.applicationservices;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.paging.ThreadPage;
import mops.foren.domain.repositoryabstraction.IThreadRepository;

@ApplicationService
public class ThreadService {

    private IThreadRepository threadRepository;

    public ThreadService(IThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }


    /**
     * This method get all Threads according to a special topicId.
     *
     * @param topicId The topicId the threads should be in.
     * @param page    The page we need.
     * @return the wanted list of threads.
     */
    public ThreadPage getThreads(TopicId topicId, Integer page) {
        return this.threadRepository.getThreadPageFromDB(topicId, page);
    }

    public Thread getThreadById(ThreadId threadId) {
        return this.threadRepository.getThreadById(threadId);
    }


    public void addPostInThread(ThreadId threadId, Post post) {
        this.threadRepository.addPostInThread(threadId, post);
    }


    public ThreadPage getThreadPageByVisibility(TopicId topicId, Integer page, Boolean visibility) {
        return this.threadRepository.getThreadPageFromDbByVisibility(topicId, page, visibility);
    }

    public void setThreadVisible(ThreadId threadId) {
        this.threadRepository.setThreadVisible(threadId);
    }

    public Integer countInvisibleThreads(TopicId topicId) {
        return this.threadRepository.countInvisibleThreads(topicId);
    }

    public void deleteThread(ThreadId threadId) {
        this.threadRepository.deleteThread(threadId);
    }
}
