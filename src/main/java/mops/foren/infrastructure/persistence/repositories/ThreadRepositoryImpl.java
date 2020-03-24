package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.paging.ThreadPage;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.mapper.PostMapper;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import mops.foren.infrastructure.persistence.mapper.ThreadPageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public class ThreadRepositoryImpl implements IThreadRepository {

    private static final int PAGE_SIZE = 10;

    private ThreadJpaRepository threadRepository;

    public ThreadRepositoryImpl(ThreadJpaRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    @Override
    public ThreadPage getThreadPageFromDB(TopicId topicId, Integer page) {
        Page<ThreadDTO> dtoPage = this.threadRepository
                .findThreadPageByTopic_Id(topicId.getId(), PageRequest.of(page, PAGE_SIZE));

        return ThreadPageMapper.toThreadPage(dtoPage, page);
    }

    /**
     * This method gets a thread with the given id.
     *
     * @param threadId the id of the wanted thread.
     * @return the tread with the given id.
     */
    @Override
    public Thread getThreadById(ThreadId threadId) {
        ThreadDTO threadDTO = this.threadRepository.findById(threadId.getId()).get();
        return ThreadMapper.mapThreadDtoToThread(threadDTO);
    }

    /**
     * Method to add a post with the given threadId.
     *
     * @param threadId The thread id
     * @param post     The post to add
     */
    @Override
    public void addPostInThread(ThreadId threadId, Post post) {
        ThreadDTO threadDTO = this.threadRepository.findById(threadId.getId()).get();
        PostDTO postDTO = PostMapper.mapPostToPostDto(post, threadDTO);
        threadDTO.setLastChangedTime(LocalDateTime.now());
        threadDTO.getPosts().add(postDTO);
        this.threadRepository.save(threadDTO);
    }
}
