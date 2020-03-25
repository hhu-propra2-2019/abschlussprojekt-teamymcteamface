package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Thread {
    private final ThreadId id;

    private final TopicId topicId;

    private String title;

    private String description;

    private User author;

    private Boolean anonymous;

    private Boolean visible;

    private Boolean moderated;

    private LocalDateTime lastPostTime;

    private ForumId forumId;


    /**
     * This method formats the lastPostTime into a nice String representation.
     * "Es gab noch keinen Beitrag in diesem Thread" will be returned if the Thread has no posts.
     *
     * @return a Date formatted in a String.
     */
    public String getFormattedDate() {
        if (this.lastPostTime == null) {
            return "Es gab noch keinen Beitrag in diesem Thread";
        }
        return this.lastPostTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }
}
