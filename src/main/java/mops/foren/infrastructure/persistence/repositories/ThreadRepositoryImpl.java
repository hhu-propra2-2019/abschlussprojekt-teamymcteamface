package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.Topic;
import mops.foren.domain.repositoryabstraction.IThreadRepository;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ThreadRepositoryImpl implements IThreadRepository {

    ThreadJpaRepository threadRepository;

    public ThreadRepositoryImpl(ThreadJpaRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    @Override
    public List<Thread> getThreadsFromTopic(Topic topic) {
        List<ThreadDTO> threadDtos = threadRepository.findByTopic(topic.getId().getId());
        return threadDtos.stream()
                .map(ThreadMapper::mapThreadDtoToThread)
                .collect(Collectors.toList());
    }

    @Override
    public Thread getThreadById(ThreadId threadId) {
        ThreadDTO threadDTO = threadRepository.findById(threadId.getId()).get();
        return ThreadMapper.mapThreadDtoToThread(threadDTO);
    }
}
