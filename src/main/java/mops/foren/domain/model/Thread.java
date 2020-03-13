package mops.foren.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Thread {
    private final ThreadId id;

    private final TopicId topicId;

    private String title;

    private String description;

}
