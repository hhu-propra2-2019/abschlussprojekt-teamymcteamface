package mops.foren.domain.model;

import lombok.RequiredArgsConstructor;

import java.sql.Date;

@RequiredArgsConstructor
public class Post {
    private PostId id;

    private ThreadId threadId;

    final User author;

    final Date creationDate;

    boolean edited;

    private String text;

    void edit(Post post) {
        throw new UnsupportedOperationException();
    }
}
