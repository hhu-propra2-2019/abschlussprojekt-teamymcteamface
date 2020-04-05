package mops.foren.infrastructure.web;

import lombok.Value;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static mops.foren.infrastructure.web.ValidationService.MAX_CONTENT_LENGTH;
import static mops.foren.infrastructure.web.ValidationService.MIN_CONTENT_LENGTH;

@Value
public class PostForm {

    @NotNull(message = "Thread title cannot be null.")
    @Size(min = MIN_CONTENT_LENGTH, max = MAX_CONTENT_LENGTH,
            message = "Post content must be between "
                    + MIN_CONTENT_LENGTH + " and "
                    + MAX_CONTENT_LENGTH + " characters.")
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
