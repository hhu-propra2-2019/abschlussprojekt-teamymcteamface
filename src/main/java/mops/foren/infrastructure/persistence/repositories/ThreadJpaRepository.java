package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadJpaRepository extends PagingAndSortingRepository<ThreadDTO, Long> {
    Page<ThreadDTO> findThreadPageByTopic_Id(Long id, Pageable pageable);

    Page<ThreadDTO> findThreadPageByTopic_IdAndVisible(Long id, Boolean Visable, Pageable pageable);
}
