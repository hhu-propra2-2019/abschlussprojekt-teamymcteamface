package mops.foren.domain.services;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.repositoryabstraction.IPostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DomainService
public class ThreadModelService {

    private IPostRepository postRepository;

    public ThreadModelService(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * This methode updates the latestPostTime in each thread.
     *
     * @param threads List of threads, where the last post time should be updated.
     */
    public void updateLastPostTime(List<Thread> threads) {
        for (Thread thread : threads) {
            Optional<LocalDateTime> maxDate = getLatestDateFromThread(thread);
            if (maxDate.isPresent()) {
                thread.setLastPostTime(maxDate.get());
            } else {
                thread.setLastPostTime(null);
            }
        }
    }

    private Optional<LocalDateTime> getLatestDateFromThread(Thread thread) {
        List<Post> posts = postRepository.getAllPostsByThreadId(thread.getId());
        List<LocalDateTime> dates = getDatesFromPosts(posts);
        return getMaxDate(dates);
    }

    private Optional<LocalDateTime> getMaxDate(List<LocalDateTime> dates) {
        return dates.stream()
                .max(LocalDateTime::compareTo);
    }

    private List<LocalDateTime> getDatesFromPosts(List<Post> posts) {
        return posts.stream()
                .map(Post::getCreationDate)
                .collect(Collectors.toList());
    }
}
