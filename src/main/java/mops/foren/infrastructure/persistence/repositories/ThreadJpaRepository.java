package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadJpaRepository extends PagingAndSortingRepository<ThreadDTO, Long> {
    Page<ThreadDTO> findThreadPageByTopic_Id(Long id, Pageable pageable);

    Page<ThreadDTO> findThreadPageByTopic_IdAndVisible(Long id, Boolean visible, Pageable pageable);

    Integer countThreadDTOByVisibleAndTopic_Id(Boolean visible, Long id);

    List<ThreadDTO> findThreadDTOByForum_Id(Long forumId);
}
