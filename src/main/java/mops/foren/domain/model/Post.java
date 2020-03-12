package mops.foren.domain.model;

import lombok.RequiredArgsConstructor;

import java.sql.Date;

@RequiredArgsConstructor
public class Post {
    private final PostId id;

    private final ThreadId threadId;

    private final User author;

    private final Date creationDate;

    private String text;

}
