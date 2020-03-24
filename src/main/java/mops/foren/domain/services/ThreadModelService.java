package mops.foren.domain.services;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.domain.repositoryabstraction.IThreadRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DomainService
public class ThreadModelService {

    private IPostRepository postRepository;
    private IThreadRepository threadRepository;

    public ThreadModelService(IPostRepository postRepository, IThreadRepository threadRepository) {
        this.postRepository = postRepository;
        this.threadRepository = threadRepository;
    }

    /**
     * This method updates the latestPostTime in the thread.
     *
     * @param threadId the thread, where the last post time should be updated.
     */
    public void updateLastPostTime(ThreadId threadId) {
        Thread thread = threadRepository.getThreadById(threadId);
        List<Post> posts = postRepository.getAllPostsByThreadId(threadId);
        Optional<LocalDateTime> maxDate = getLatestDateFromThread(posts);
        if (maxDate.isPresent()) {
            thread.setLastPostTime(maxDate.get());
        } else {
            thread.setLastPostTime(null);
        }
    }


    private Optional<LocalDateTime> getLatestDateFromThread(List<Post> posts) {
        if (posts.isEmpty()) {
            return Optional.empty();
        }
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
