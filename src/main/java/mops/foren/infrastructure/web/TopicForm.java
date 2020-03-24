package mops.foren.infrastructure.web;

import lombok.Value;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;

@Value
public class TopicForm {
    private final String title;
    private final String description;
    private final Boolean moderated;
    private final Boolean anonymous;


    /**
     * Method to build a post out of the postContent.
     *
     * @param forumId The id of the thread the post is in.
     * @return The resulting post object.
     */
    public Topic getTopic(ForumId forumId) {
        return Topic.builder()
                .title(this.title)
                .anonymous(this.anonymous)
                .description(this.description)
                .moderated(this.moderated)
                .forumId(forumId)
                .build();
    }
}
