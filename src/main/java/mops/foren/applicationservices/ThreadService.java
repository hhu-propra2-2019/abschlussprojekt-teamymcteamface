package mops.foren.applicationservices;

import mops.foren.domain.model.Permission;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IThreadRepository;

import java.util.List;

public class ThreadService {

    private IThreadRepository threadRepository;

    public ThreadService(IThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public List<Thread> getThreads(TopicId topicId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method to add a thread.
     *
     * @param thread  The thread to add
     * @param user    The user that wants to create the thread
     * @param topicId The topicId the thread belongs to
     */
    public void addThread(Thread thread, User user, TopicId topicId) {
        if (user.checkPermission(topicId, Permission.CREATE_THREAD)) {
            // ADD
        }
    }
}
