package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public abstract class PostMapper {
    @Autowired
    private static UserJpaRepository userRepository;


    /**
     * This method maps an postDTO object to the corresponding Post object.
     *
     * @param postDTO that should be mapped.
     * @return the resulting Post.
     */
    public static Post mapPostDtoToPost(PostDTO postDTO) {
        UserDTO authorDto = postDTO.getAuthor();
        User author = UserMapper.mapUserDtoToUser(authorDto);
        return Post.builder()
                .id(new PostId(postDTO.getId()))
                .threadId(new ThreadId(postDTO.getThread().getId()))
                .author(author)
                .text(postDTO.getText())
                .creationDate(LocalDateTime.parse(postDTO.getDateTime().toString()))
                .build();
    }
}
