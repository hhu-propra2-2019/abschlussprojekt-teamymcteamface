package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.*;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.domain.repositoryabstraction.ITopicRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TopicRepositoryImpl implements ITopicRepository {
    private TopicJpaRepository topicRepository;

    public TopicRepositoryImpl(TopicJpaRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * This method get the topics of an specific forum from the db.
     *
     * @param forum which forums are requested.
     * @return a list of topics from the user.
     */
    @Override
    public List<Topic> getTopicsFromDB(TopicId topicId) {
        List<TopicDTO> topicDtos = getTopicDTOs(user);
        List<Topic> topicList = getAllTopics(topicDtos);
        return topicList;
    }

    private List<ForumDTO> getTopicDTOs(Forum forum) {
        return forum.getTopics().stream()
                .map(forumId -> topicRepository.findById(forum.getId().getId()))
                .map(forumDTO -> forumDTO.get())
                .collect(Collectors.toList());
    }


    private List<Topic> getAllTopics(List<TopicDTO> topicDtos) {
        return topicDtos.stream()
                .map(TopicMapper::mapTopicDtoToTopic)
                .collect(Collectors.toList());
    }

    public Topic getOneTopicFromDB(TopicId topicId) {
        return topicRepository.findById(topicId.getId()).stream()
                .map(TopicMapper::mapTopicDtoToTopic)
                .collect(Collectors.toList()).get(0);
    }
}
