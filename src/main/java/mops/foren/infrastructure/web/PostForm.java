package mops.foren.infrastructure.web;

import lombok.Value;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;

@Value
public class PostForm {
    private final String postContent;

    public Post getPost(User user, ThreadId threadId) {
        return Post.builder()
                .changed(false)
                .text(this.postContent)
                .author(user)
                .threadId(threadId)
                .build();
    }
}
