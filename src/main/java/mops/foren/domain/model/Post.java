package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Post {
    private final PostId id;

    private final ThreadId threadId;

    private final User author;

    private final LocalDateTime creationDate;

    private String text;

    private Boolean changed;

}
