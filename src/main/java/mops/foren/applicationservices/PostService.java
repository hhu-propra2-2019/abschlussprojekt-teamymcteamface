package mops.foren.applicationservices;

import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.domain.services.ThreadModelService;

@ApplicationService
public class PostService {

    private IPostRepository postRepository;
    private ThreadModelService threadModelService;

    public PostService(IPostRepository postRepository, ThreadModelService threadModelService) {
        this.postRepository = postRepository;
        this.threadModelService = threadModelService;
    }

    public PostPage getPosts(ThreadId threadId, Integer page) {
        return this.postRepository.getPostPageFromDB(threadId, page);
    }

    public Post getPost(PostId postId) {
        return this.postRepository.getPostById(postId);
    }

    /**
     * Method to add a post.
     *
     * @param post    The post to add
     * @param user    The user that wants to create the post
     * @param forumId The threadId the post belongs to
     */
    public void addPost(Post post, User user, ForumId forumId) {
        if (user.checkPermission(forumId, Permission.CREATE_POST)) {
            // ADD
        }

    }

    /**
     * This method should delete a post.
     * This method also updates the lastPostTime changed in Threads.
     *
     * @param post The post that should be deleted.
     */
    public void deletePost(Post post) {
        this.postRepository.deletePostById(post.getId());
    }

}
