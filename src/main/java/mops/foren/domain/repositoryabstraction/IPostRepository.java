package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.User;

import java.util.List;

public interface IPostRepository {
    List<Post> getPostsFromThread(Thread thread);

    Post getPostById(PostId postId);

    List<Post> getPostsFromUser(User user);
}
