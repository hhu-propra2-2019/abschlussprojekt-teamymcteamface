package mops.foren.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class Thread {
    private final ThreadId id;

    private final String author;

    @Getter
    private final TopicId topicId;

    private String title;

    private String description;

    private final LocalDateTime dateCreated = LocalDateTime.now();

    public String getLatestUpdate() {
        return this.dateCreated.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }

}
