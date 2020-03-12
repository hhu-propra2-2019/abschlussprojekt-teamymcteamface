package mops.foren.domain.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Forum {
    private final ForumId id;

    private String title;

    private String description;

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }
}
