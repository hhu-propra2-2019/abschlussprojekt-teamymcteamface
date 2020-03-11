package mops.foren.domain.model;

public class Forum {
    private Long id;

    String title;

    String description;

    Course course;

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }
}
