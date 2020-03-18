package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.PostDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostJpaRepository extends CrudRepository<PostDTO, Long> {
    List<PostDTO> findByThread_Id(Long id);

    List<PostDTO> findByAuthor(String name);
}
