package mops.foren.infrastructure.web;

import lombok.Value;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;

@Value
public class PostForm {
    private final String postContent;


    /**
     * This method maps return the corresponding Post.
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
