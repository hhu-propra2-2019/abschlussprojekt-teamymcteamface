package mops.foren.infrastructure.persistence.dtos;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;
import org.springframework.stereotype.Service;

@Service
public abstract class TopicMapper {

    /**
     * This method maps a ForumDto object to the corresponding Forum object.
     *
     * @param topicDTO a TopicDTO.
     * @return the corresponding Forum.
     */
    public static Topic mapTopicDtoToTopic(TopicDTO topicDTO) {
        return Topic.builder()
                .id(new TopicId(topicDTO.getId()))
                .title(topicDTO.getTitle())
                .description(topicDTO.getDescription())
                .forumId(new ForumId(topicDTO.getForum().getId()))
                .build();
    }

    /**
     * Method to map a Topic on the corresponding TopicDTO.
     *
     * @param topic    The topic
     * @param forumDTO The ForumDTO
     * @return The TopicDTO
     */
    public static TopicDTO mapTopicToTopicDto(Topic topic, ForumDTO forumDTO) {
        return TopicDTO.builder()
                .forum(forumDTO)
                .description(topic.getDescription())
                .title(topic.getTitle())
                .moderated(topic.getModerated())
                .build();
    }
}
