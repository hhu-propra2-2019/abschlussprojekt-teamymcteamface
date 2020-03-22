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

    private LocalDateTime lastPostTime;

    public String getFormattedDate() {
        if (lastPostTime == null) {
            return "Es gab noch keinen Beitrag in diesem Thread";
        }
        return this.lastPostTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }
}
