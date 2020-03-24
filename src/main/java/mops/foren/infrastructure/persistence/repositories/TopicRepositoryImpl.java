package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.Topic;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.repositoryabstraction.ITopicRepository;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
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
     * @param forumId - id of the requested forum
     * @return a list of topics from the user.
     */
    @Override
    public List<Topic> getTopicsFromDB(ForumId forumId) {
        List<TopicDTO> topicDtos = this.topicRepository.findAllByForum_Id(forumId.getId());
        return getAllTopics(topicDtos);
    }


    private List<Topic> getAllTopics(List<TopicDTO> topicDtos) {
        return topicDtos.stream()
                .map(TopicMapper::mapTopicDtoToTopic)
                .collect(Collectors.toList());
    }

    /**
     * Method to get one topic from the DB.
     *
     * @param topicId the topic id
     * @return the topic
     */
    @Override
    public Topic getOneTopicFromDB(TopicId topicId) {
        TopicDTO topicDto = this.topicRepository.findById(topicId.getId()).get();
        return TopicMapper.mapTopicDtoToTopic(topicDto);
    }

    /**
     * This method adds the given thread to the given topic and uses its description
     * to generate the first post.
     *
     * @param topicId the topic in which the new thread should be added
     * @param thread  the thread to add
     */
    @Override
    public void addThreadInTopic(TopicId topicId, Thread thread) {
        TopicDTO topicDTO = this.topicRepository.findById(topicId.getId()).get();
        ThreadDTO threadDTO = addPostDTOToThreadDTO(
                ThreadMapper.mapThreadToThreadDto(thread, topicDTO));
        topicDTO.getThreads().add(threadDTO);
        this.topicRepository.save(topicDTO);
    }

    /**
     * Private helper function which generates a new postDTO from the given topicDTO's
     * description and adds it to the threadDTO.
     *
     * @param threadDTO the threadDTO to which the new postDTO will be added
     * @return the threadDTO with the new postDTO added to its posts list
     */
    private ThreadDTO addPostDTOToThreadDTO(ThreadDTO threadDTO) {
        PostDTO postDTO = PostDTO.builder()
                .text(threadDTO.getDescription())
                .author(threadDTO.getAuthor())
                .dateTime(LocalDateTime.now())
                .thread(threadDTO)
                .build();

        threadDTO.setPosts(Arrays.asList(postDTO));

        return threadDTO;
    }
}
