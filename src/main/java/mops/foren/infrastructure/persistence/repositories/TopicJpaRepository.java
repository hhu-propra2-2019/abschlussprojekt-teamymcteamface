package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicJpaRepository extends CrudRepository<TopicDTO, Long> {
}
