package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import org.springframework.stereotype.Service;

@Service
public abstract class ThreadMapper {

    /**
     * This method maps a ThreadDto object to the corresponding Thread object.
     *
     * @param threadDTO a ThreadDTO.
     * @return the corresponding Thread.
     */
    public static Thread mapThreadDtoToThread(ThreadDTO threadDTO) {
        return Thread.builder()
                .id(new ThreadId(threadDTO.getId()))
                .topicId(new TopicId(threadDTO.getTopic().getId()))
                .lastPostTime(threadDTO.getLastChangedTime())
                .author(UserMapper.mapUserDtoToUser(threadDTO.getAuthor()))
                .title(threadDTO.getTitle())
                .description(threadDTO.getDescription())
                .build();
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
                .description(thread.getDescription())
                .title(thread.getTitle())
                .topic(topicDTO)
                .build();
    }
}
