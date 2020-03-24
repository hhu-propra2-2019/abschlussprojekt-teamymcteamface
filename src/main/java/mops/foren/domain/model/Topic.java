package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Topic {
    private final TopicId id;

    private final ForumId forumId;

    private String title;

    private String description;

    private Boolean moderated;

    private Boolean anonymous;
}
