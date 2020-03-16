package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumJpaRepository extends CrudRepository<ForumDTO, Long> {
}
