package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Forum;
import mops.foren.domain.repositoryabstraction.IForumRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.repositories.ForumDtoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MapperForum implements IForumRepository {
    ForumDtoRepository forumRepository;

    public MapperForum(ForumDtoRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public List<Forum> getForums(List<Long> forumIds) {
        if (forumIds == null) {
            return new ArrayList<>();
        }
        List<ForumDTO> forumDtos = forumRepository.findAllById(forumIds);
        List<Forum> forumList = forumDtos.stream()
                .map(f -> Forum.builder().id(f.getId())
                        .title(f.getTitle())
                        .description(f.getDescription())
                        .build())
                .collect(Collectors.toList());
        return forumList;
    }
}
