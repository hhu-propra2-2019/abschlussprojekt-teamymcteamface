package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ForumRepositoryImpl implements IForumRepository {

    private ForumJpaRepository forumRepository;

    public ForumRepositoryImpl(ForumJpaRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * This method get the forums of an specific user from the db.
     *
     * @param user which forums are requested.
     * @return a list of forums from the user.
     */
    @Override
    public List<Forum> getForumsFromDB(User user) {
        List<ForumDTO> forumDtos = getForumDTOs(user);
        List<Forum> forumList = getAllForums(forumDtos);
        return forumList;
    }

    private List<ForumDTO> getForumDTOs(User user) {
        return user.getForums().stream()
                .map(forumId -> this.forumRepository.findById(forumId.getId()))
                .map(forumDTO -> forumDTO.get())
                .collect(Collectors.toList());
    }


    private List<Forum> getAllForums(List<ForumDTO> forumDtos) {
        return forumDtos.stream()
                .map(ForumMapper::mapForumDtoToForum)
                .collect(Collectors.toList());
    }

    @Override
    public Forum getOneForumFromDB(ForumId forumId) {
        ForumDTO forumDTO = this.forumRepository.findById(forumId.getId()).get();
        return ForumMapper.mapForumDtoToForum(forumDTO);
    }

    @Override
    public void addTopicInForum(ForumId forumId, Topic topic) {
        ForumDTO forumDto = this.forumRepository.findById(forumId.getId()).get();
        TopicDTO topicDTO = TopicMapper.mapTopicToTopicDto(topic, forumDto);
        forumDto.getTopics().add(topicDTO);
        this.forumRepository.save(forumDto);
    }
}
