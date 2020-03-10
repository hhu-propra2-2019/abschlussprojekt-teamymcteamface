package mops.foren.domain.model;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Course {

    final String semester;

    String title;

    List<User> lecturer;

    List<User> participants;

    void addLecturer(User lecturer) {
        throw new UnsupportedOperationException();
    }

    void deleteLecturer(User lecturer) {
        throw new UnsupportedOperationException();
    }

    void addParticipant(User participant) {
        throw new UnsupportedOperationException();
    }

    void deleteParticipant(User participant) {
        throw new UnsupportedOperationException();
    }

}
