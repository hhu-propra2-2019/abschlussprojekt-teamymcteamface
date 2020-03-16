package mops.foren.domain.model;

import lombok.Data;

@Data
public class Thread {
    private final ThreadId id;

    private final TopicId topicId;

    private String title;

    private String description;

    private User author;

}
