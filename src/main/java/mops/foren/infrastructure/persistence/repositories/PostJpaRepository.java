package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.PostDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostJpaRepository extends CrudRepository<PostDTO, Long> {
}
