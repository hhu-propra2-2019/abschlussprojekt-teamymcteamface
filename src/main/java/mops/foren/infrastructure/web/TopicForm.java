package mops.foren.infrastructure.web;

import lombok.Value;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;

@Value
public class TopicForm {
    private String title;
    private String description;
    private Boolean moderated;
    private Boolean anonymous;

    /**
     * This is a constructor.
     *
     * @param title       of the topic.
     * @param description of the topic.
     * @param moderated   moderation mode of the topic.
     * @param anonymous   anonymous mode of the topic.
     */
    public TopicForm(String title, String description, Boolean moderated, Boolean anonymous) {
        this.title = title;
        this.description = description;
        this.moderated = moderated;
        this.anonymous = anonymous != null;
    }

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
