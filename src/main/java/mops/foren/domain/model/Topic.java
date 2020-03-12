package mops.foren.domain.model;

import lombok.Data;

@Data
public class Topic {
    private final TopicId id;

    private final ForumId forumId;

    private String title;

    private String description;
}
