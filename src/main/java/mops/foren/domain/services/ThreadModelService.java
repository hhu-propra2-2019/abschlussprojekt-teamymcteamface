package mops.foren.domain.services;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.domain.repositoryabstraction.IPostRepository;

import java.util.List;

@DomainService
public class ThreadModelService {

    private IPostRepository postRepository;

    public ThreadModelService(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void updateLastPostTime(List<Thread> threads) {
        for (Thread thread : threads) {
            Post post = getLatestPostFromThread(thread);
            if (post == null) {
                thread.setLastPostTime(null);
            } else {
                thread.setLastPostTime(post.getCreationDate());
            }
        }
    }

    private Post getLatestPostFromThread(Thread thread) {
        PostPage postPage = getLastPostPage(thread.getId());
        if (postPage.getPosts().size() == 0) {
            return null;
        }
        int size = postPage.getPosts().size();
        return postPage.getPosts().get(size - 1);
    }

    private PostPage getLastPostPage(ThreadId id) {
        PostPage postPage = postRepository.getPostPageFromDB(id, 0);
        int totalPages = postPage.getPaging().getTotalPages();
        if (totalPages == 0) {
            return postPage;
        }
        return postRepository.getPostPageFromDB(id, totalPages - 1);
    }

}
