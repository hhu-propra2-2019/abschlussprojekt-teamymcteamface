package mops.foren.domain.model;

import java.util.List;

public class Forum {
    String title;

    String description;

    Course course;

    List<Thread> threads;

    List<User> users;

    public void addUser(User user) {
        throw new UnsupportedOperationException();
    }

    public void deleteUser(User user) {
        throw new UnsupportedOperationException();
    }


    public void addTopic(Topic topic) {
        throw new UnsupportedOperationException();
    }

    public void deleteTopic(Topic topic) {
        throw new UnsupportedOperationException();
    }

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }
}
