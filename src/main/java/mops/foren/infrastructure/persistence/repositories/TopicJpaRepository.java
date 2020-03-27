package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.TopicDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicJpaRepository extends CrudRepository<TopicDTO, Long> {
    List<TopicDTO> findAllByForum_Id(Long id);

    // added for testing
    Boolean existsByForum_Id(Long id);
}
