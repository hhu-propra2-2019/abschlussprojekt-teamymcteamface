package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
public abstract class ThreadMapper {

    /**
     * This method maps a ThreadDto object to the corresponding Thread object.
     *
     * @param threadDTO a ThreadDTO.
     * @return the corresponding Thread.
     */
    public static Thread mapThreadDtoToThread(ThreadDTO threadDTO) {
        LocalDateTime lastChange = getLastPostTime(threadDTO.getPosts());
        Long count = getCountOfInvisiblePosts(threadDTO.getModerated(), threadDTO.getPosts());
        return Thread.builder()
                .id(new ThreadId(threadDTO.getId()))
                .anonymous(threadDTO.getAnonymous())
                .moderated(threadDTO.getModerated())
                .unModerated(count)
                .visible(threadDTO.getVisible())
                .topicId(new TopicId(threadDTO.getTopic().getId()))
                .lastPostTime(lastChange)
                .author(UserMapper.mapUserDtoToUser(threadDTO.getAuthor()))
                .title(threadDTO.getTitle())
                .description(threadDTO.getDescription())
                .forumId(new ForumId(threadDTO.getForum().getId()))
                .build();
    }

    private static Long getCountOfInvisiblePosts(Boolean moderated, List<PostDTO> posts) {
        if (!moderated) {
            return 0L;
        }
        return posts.stream()
                .filter(not(PostDTO::getVisible))
                .count();
    }

    private static LocalDateTime getLastPostTime(List<PostDTO> posts) {
        Optional<LocalDateTime> max = posts.stream()
                .filter(PostDTO::getVisible)
                .map(PostDTO::getDateTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo);
        return max.orElse(null);
    }

    /**
     * Method to map a Thread on the corresponsing ThreadDto.
     *
     * @param thread   the thread
     * @param topicDTO the topicDTO that the thread is in
     * @return The ThreadDto
     */
    public static ThreadDTO mapThreadToThreadDto(Thread thread, TopicDTO topicDTO) {
        return ThreadDTO.builder()
                .author(UserMapper.mapUserToUserDto(thread.getAuthor()))
                .anonymous(topicDTO.getAnonymous())
                .moderated(topicDTO.getModerated())
                .visible(thread.getVisible())
                .description(thread.getDescription())
                .title(thread.getTitle())
                .topic(topicDTO)
                .forum(topicDTO.getForum())
                .build();
    }
}
