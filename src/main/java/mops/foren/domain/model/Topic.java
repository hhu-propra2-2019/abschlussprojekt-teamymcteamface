package mops.foren.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Topic {
    private final TopicId id;

    private final ForumId forumId;

    private String title;

    private String description;
}
