package mops.foren.infrastructure.web;

import lombok.Value;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;

import javax.validation.constraints.NotBlank;

@Value
public class PostForm {

    // For now only check for Null until there is a content class
    @NotBlank(message = "Post content cannot be blank.")
    private final String postContent;

    /**
     * Method to build a post out of the postContent.
     *
     * @param user     The user, that made the post.
     * @param threadId The id of the thread the post is in.
     * @return The resulting post object.
     */
    public Post getPost(User user, ThreadId threadId) {
        return Post.builder()
                .changed(false)
                .text(this.postContent)
                .author(user)
                .threadId(threadId)
                .build();
    }
}
