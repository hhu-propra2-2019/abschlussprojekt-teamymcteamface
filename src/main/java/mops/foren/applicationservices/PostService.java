package mops.foren.applicationservices;

import mops.foren.domain.model.*;
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

}
