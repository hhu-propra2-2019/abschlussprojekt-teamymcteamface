package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
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
                .title(threadDTO.getTitle())
                .description(threadDTO.getDescription())
                .build();
    }

}
