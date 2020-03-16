package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import org.springframework.stereotype.Service;

@Service
public abstract class ForumMapper {

    /**
     * This method maps a ForumDto object to a Forum object.
     *
     * @param f a ForumDTO.
     * @return the corresponding Forum.
     */
    public static Forum mapForumDtoToForum(ForumDTO f) {
        return Forum.builder().id(new ForumId(f.getId()))
                .title(f.getTitle())
                .description(f.getDescription())
                .build();
    }


    /**
     * This method maps a Forum object to a ForumDTO object.
     *
     * @param forum a forum object.
     * @return the corresponding ForumDTO.
     */
    public static ForumDTO mapForumToForumDTO(Forum forum) {
        return ForumDTO.builder()
                .title(forum.getTitle())
                .description(forum.getDescription())
                .build();
    }
}
