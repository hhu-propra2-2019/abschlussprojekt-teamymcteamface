package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Thread {
    private final ThreadId id;

    private final TopicId topicId;

    private String title;

    private String description;

    private String author;

}
