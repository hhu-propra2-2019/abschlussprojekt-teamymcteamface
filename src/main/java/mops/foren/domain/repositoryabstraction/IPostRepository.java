package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.*;
import mops.foren.domain.model.Thread;

import java.util.List;

public interface IPostRepository {
    List<Post> getPostsFromThread(Thread thread);

    List<Post> getPostsFromDB(ThreadId threadId);

    Post getPostById(PostId postId);

    List<Post> getPostsFromUser(User user);
}
