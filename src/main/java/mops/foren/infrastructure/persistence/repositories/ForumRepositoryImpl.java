package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ForumRepositoryImpl implements IForumRepository {
    ForumJpaRepository forumRepository;

    public ForumRepositoryImpl(ForumJpaRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public List<Forum> getForums(User user) {
        List<ForumDTO> forumDtos = getFroumDTOs(user);
        List<Forum> forumList = getAllForums(forumDtos);
        return forumList;
    }

    private List<ForumDTO> getFroumDTOs(User user) {
        return user.getForums().stream()
                .map(f -> forumRepository.findById(f.getId()))
                .map(forumDTO -> forumDTO.get())
                .collect(Collectors.toList());
    }


    private List<Forum> getAllForums(List<ForumDTO> forumDtos) {
        return forumDtos.stream()
                .map(ForumMapper::mapForumDtoToForum)
                .collect(Collectors.toList());
    }
}
