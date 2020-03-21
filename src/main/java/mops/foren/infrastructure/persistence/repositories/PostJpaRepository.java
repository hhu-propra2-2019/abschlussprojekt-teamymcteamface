package mops.foren.infrastructure.persistence.repositories;

import mops.foren.infrastructure.persistence.dtos.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostJpaRepository extends PagingAndSortingRepository<PostDTO, Long> {
    Page<PostDTO> findByThread_Id(Long id, Pageable pageable);

    List<PostDTO> findByAuthor_Username(String name);
}
