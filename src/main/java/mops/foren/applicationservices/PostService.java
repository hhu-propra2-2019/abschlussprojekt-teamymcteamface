package mops.foren.applicationservices;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.domain.repositoryabstraction.IPostRepository;

@ApplicationService
public class PostService {

    private IPostRepository postRepository;

    public PostService(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostPage getPosts(ThreadId threadId, Integer page) {
        return this.postRepository.getPostPageFromDB(threadId, page);
    }

    public Post getPost(PostId postId) {
        return this.postRepository.getPostById(postId);
    }

    public void deletePost(PostId postId) {
        this.postRepository.deletePostById(postId);
    }

    public PostPage searchWholeForum(ForumId forumId, String content, Integer page) {
        return this.postRepository.searchWholeForumForContent(forumId, content, page);
    }

    public void setPostVisible(PostId postId) {
        this.postRepository.setPostVisible(postId);
    }
}
