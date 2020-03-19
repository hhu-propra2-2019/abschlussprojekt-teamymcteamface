package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;

import java.util.List;

public interface IPostRepository {
    List<Post> getPostsFromDB(ThreadId threadId);

    Post getPostById(PostId postId);

    List<Post> getPostsFromUser(User user);
}
