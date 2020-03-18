package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.*;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.domain.repositoryabstraction.ITopicRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TopicRepositoryImpl implements ITopicRepository {
    private TopicJpaRepository topicRepository;
    private ForumJpaRepository forumRepository;

    public TopicRepositoryImpl(TopicJpaRepository topicRepository, ForumJpaRepository forumRepository) {
        this.topicRepository = topicRepository;
        this.forumRepository = forumRepository;
    }

    /**
     * This method get the topics of an specific forum from the db.
     *
     * @param forum which forums are requested.
     * @return a list of topics from the user.
     */
    @Override
    public List<Topic> getTopicsFromDB(ForumId forumId) {
        List<TopicDTO> topicDtos = getTopicDTOs(forumRepository.findById(forumId.getId()).get());
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

    public Topic getOneTopicFromDB(TopicId topicId) {
        TopicDTO topicDto = topicRepository.findById(topicId.getId()).get();

        return TopicMapper.mapTopicDtoToTopic(topicDto);
    }
}
