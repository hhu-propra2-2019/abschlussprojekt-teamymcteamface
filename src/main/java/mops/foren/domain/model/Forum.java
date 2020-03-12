package mops.foren.domain.model;

public class Forum {
    private ForumId id;

    String title;

    String description;

    Course course;

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }
}
