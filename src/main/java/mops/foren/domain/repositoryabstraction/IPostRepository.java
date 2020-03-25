package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.PostPage;

import java.util.List;

public interface IPostRepository {
    PostPage getPostPageFromDB(ThreadId threadId, Integer page);

    Post getPostById(PostId postId);

    List<Post> getPostsFromUser(User user);

    List<Post> getAllPostsByThreadId(ThreadId id);

    PostPage searchWholeForumForContent(ForumId forumId, String content, Integer page);

    void setPostVisible(PostId postId);
}
