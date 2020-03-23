package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadJpaRepository extends CrudRepository<ThreadDTO, Long> {
    Page<ThreadDTO> findByTopic_Id(Long id, Pageable pageable);
}
