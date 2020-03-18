package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.repositoryabstraction.ITopicRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TopicRepositoryImpl implements ITopicRepository {
    private TopicJpaRepository topicRepository;
    private ForumJpaRepository forumRepository;

    public TopicRepositoryImpl(TopicJpaRepository topicRepository,
                               ForumJpaRepository forumRepository) {
        this.topicRepository = topicRepository;
        this.forumRepository = forumRepository;
    }

    /**
     * This method get the topics of an specific forum from the db.
     *
     * @param forumId - id of the requested forum
     * @return a list of topics from the user.
     */
    @Override
    public List<Topic> getTopicsFromDB(ForumId forumId) {
        ForumDTO forumDto = this.forumRepository.findById(forumId.getId()).get();
        List<TopicDTO> topicDtos = getTopicDTOs(forumDto);
        List<Topic> topicList = getAllTopics(topicDtos);
        return topicList;
    }

    private List<TopicDTO> getTopicDTOs(ForumDTO forum) {
        return new ArrayList<>(forum.getTopics());
    }


    private List<Topic> getAllTopics(List<TopicDTO> topicDtos) {
        return topicDtos.stream()
                .map(TopicMapper::mapTopicDtoToTopic)
                .collect(Collectors.toList());
    }

    @Override
    public Topic getOneTopicFromDB(TopicId topicId) {
        TopicDTO topicDto = this.topicRepository.findById(topicId.getId()).get();

        return TopicMapper.mapTopicDtoToTopic(topicDto);
    }
}
