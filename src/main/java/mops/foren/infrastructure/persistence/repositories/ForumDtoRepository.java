package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumDtoRepository extends CrudRepository<ForumDTO, Long> {
    @Override
    List<ForumDTO> findAllById(Iterable<Long> longs);

    ForumDTO findOneForumByTitle(String title);
}
