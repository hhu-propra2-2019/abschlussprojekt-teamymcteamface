package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Post {
    private final PostId id;

    private final ThreadId threadId;

    private final User author;

    private final ForumId forumId;

    private final LocalDateTime creationDate;

    private String text;

    private Boolean changed;

    private Boolean anonymous;

    private Boolean visible;


    public String getFormattedDate() {
        return this.creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }

}
