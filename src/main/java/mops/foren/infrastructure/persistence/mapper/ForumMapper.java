package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import org.springframework.stereotype.Service;

@Service
public abstract class ForumMapper {

    /**
     * This method maps a ForumDto object to the corresponding Forum object.
     *
     * @param forumDTO a ForumDTO.
     * @return the corresponding Forum.
     */
    public static Forum mapForumDtoToForum(ForumDTO forumDTO) {
        return Forum.builder()
                .id(new ForumId(forumDTO.getId()))
                .title(forumDTO.getTitle())
                .description(forumDTO.getDescription())
                .build();
    }

    /**
     * This method maps a Forum object to a ForumDTO object.
     *
     * @param forum a forum object.
     * @return the corresponding ForumDTO.
     */
    public static ForumDTO mapForumToForumDTO(Forum forum) {
        ForumDTO.ForumDTOBuilder builder = ForumDTO.builder()
                .title(forum.getTitle())
                .description(forum.getDescription());

        if (forum.getId() != null) {
            builder.id(forum.getId().getId());
        }

        return builder.build();
    }
}
