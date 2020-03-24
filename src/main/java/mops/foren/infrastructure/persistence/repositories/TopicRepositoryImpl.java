package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.*;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import mops.foren.domain.repositoryabstraction.ITopicRepository;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import mops.foren.infrastructure.persistence.mapper.TopicMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TopicRepositoryImpl implements ITopicRepository {
    private TopicJpaRepository topicRepository;
    private ThreadJpaRepository threadRepository;
    private IThreadRepository threadRepositoryImpl;

    /**
     * Public constructor.
     *
     * @param topicRepository      injected topicJpaRepository
     * @param threadRepository     injected threadJpaRepository
     * @param threadRepositoryImpl injected threadRepositoryImpl through interface
     */
    public TopicRepositoryImpl(TopicJpaRepository topicRepository,
                               ThreadJpaRepository threadRepository,
                               IThreadRepository threadRepositoryImpl) {
        this.topicRepository = topicRepository;
        this.threadRepository = threadRepository;
        this.threadRepositoryImpl = threadRepositoryImpl;
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
        ThreadDTO threadDTO = ThreadMapper.mapThreadToThreadDto(thread, topicDTO);
        threadDTO.setPosts(new ArrayList<PostDTO>());

        // Needs to be saved to get Id
        ThreadDTO savedThread = this.threadRepository.save(threadDTO);

        Post firstPost = Post.builder()
                .author(thread.getAuthor())
                .text(thread.getDescription())
                .creationDate(LocalDateTime.now())
                .changed(false)
                .build();

        this.threadRepositoryImpl.addPostInThread(new ThreadId(savedThread.getId()), firstPost);
    }
}
