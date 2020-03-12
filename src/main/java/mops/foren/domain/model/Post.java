package mops.foren.domain.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Post {
    private final PostId id;

    private final ThreadId threadId;

    private final User author;

    private final Date creationDate;

    private String text;

    private Boolean changed;

}
