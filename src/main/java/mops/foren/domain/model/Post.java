package mops.foren.domain.model;

import lombok.RequiredArgsConstructor;

import java.sql.Date;

@RequiredArgsConstructor
public class Post {
    private PostId id;

    private ThreadId threadId;

    private final User author;

    private final Date creationDate;

    private String text;

}
