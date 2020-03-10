package mops.foren.domain.model;

import java.util.List;

public class Topic {
    String title;

    String description;

    boolean moderated;

    List<Thread> threads;

    void addThread(Thread thread) {
        throw new UnsupportedOperationException();
    }

    void deleteThread(Thread thread) {
        throw new UnsupportedOperationException();
    }


    void changeModerationModus() {
        throw new UnsupportedOperationException();
    }

}
