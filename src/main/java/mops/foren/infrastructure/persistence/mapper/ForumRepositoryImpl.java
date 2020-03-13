package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.repositories.ForumJpaRepository;
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

    private Forum mapForumDtoToForum(ForumDTO f) {
        return Forum.builder().id(new ForumId(f.getId()))
                .title(f.getTitle())
                .description(f.getDescription())
                .build();
    }

    private List<Forum> getAllForums(List<ForumDTO> forumDtos) {
        return forumDtos.stream()
                .map(this::mapForumDtoToForum)
                .collect(Collectors.toList());
    }
}
