package mops.foren.domain.services;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.Thread;
import mops.foren.domain.repositoryabstraction.IThreadRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DomainService
public class ForumModelService {
    private IThreadRepository threadRepository;

    public ForumModelService(IThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }


    public void updateLastChangedTime(Forum forum) {
        List<Thread> threadByForumId = this.threadRepository.getThreadByForumId(forum.getId());
        Optional<LocalDateTime> max = threadByForumId.stream()
                .map(Thread::getLastPostTime)
                .max(LocalDateTime::compareTo);
        if (max.isPresent()) {
            forum.setLastChange(max.get());
        } else {
            forum.setLastChange(null);
        }
    }
}
