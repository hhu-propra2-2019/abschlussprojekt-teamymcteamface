package mops.foren.applicationservices;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.domain.repositoryabstraction.IThreadRepository;

import java.util.ArrayList;
import java.util.List;

public class ThreadService {

    private IThreadRepository threadRepository;

    public ThreadService(IThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public List<mops.foren.domain.model.Thread> getThreads(TopicId topicId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method to get mocked threads
     *
     * @param topicId The topic id
     * @return A list of threads that belong to the topic
     */
    public List<Thread> getMockThreads(TopicId topicId) {
        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(new ThreadId(1L), "maste33", new TopicId(2L),
                "Alter, was ist eine IDE ?", "Sorry, bin etwas langsam..."));
        threads.add(new Thread(new ThreadId(2L), "chmet99", new TopicId(2L),
                "Ich bin einsam :(", "Hallo ?"));
        threads.add(new Thread(new ThreadId(2L + 1L), "jeben9000", new TopicId(2L),
                "Ich will lieber Germanistik studieren", "Echt jetzt, Informatik ist doof."));
        threads.add(new Thread(new ThreadId(2L + 2L), "lula42", new TopicId(2L),
                "Wann kriegen wie die Ergebnisse?", "Würde ich schon gerne wissen"));
        threads.add(new Thread(new ThreadId(2L + 2L + 1L), "smith100", new TopicId(2L),
                "Habe eine Frage", "Was ist eine IDE ???"));
        threads.add(new Thread(new ThreadId(2L + 2L + 2L), "gg123", new TopicId(2L),
                "Gute Website", "Checkt diese Website um das Internet zu verstehen."));

        return threads;

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
