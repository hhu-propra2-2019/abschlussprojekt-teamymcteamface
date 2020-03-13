package mops.foren.infrastructure.persistence.repositorys;


import mops.foren.infrastructure.persistence.dtos.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDtoRepository extends CrudRepository<UserDTO, String> {
}
