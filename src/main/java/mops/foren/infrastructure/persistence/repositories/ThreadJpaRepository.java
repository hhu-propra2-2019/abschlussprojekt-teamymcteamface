package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadJpaRepository extends CrudRepository<ThreadDTO, Long> {
    List<ThreadDTO> findByTopic_Id(Long id);
}
