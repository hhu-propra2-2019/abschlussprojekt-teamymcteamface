package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public abstract class PostMapper {

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
                .anonymous(postDTO.getAnonymous())
                .threadId(new ThreadId(postDTO.getThread().getId()))
                .visible(postDTO.getVisible())
                .author(author)
                .text(postDTO.getText())
                .creationDate(postDTO.getDateTime())
                .build();
    }

    /**
     * This method maps a Post to the corresponding PostDTO.
     *
     * @param post      the post that should be mapped.
     * @param threadDTO The Tread that the post lives in.
     * @return the corresponding PostDTO object.
     */
    public static PostDTO mapPostToPostDto(Post post, ThreadDTO threadDTO) {
        return PostDTO.builder()
                .author(UserMapper.mapUserToUserDto(post.getAuthor()))
                .anonymous(threadDTO.getAnonymous())
                .visible(post.getVisible())
                .thread(threadDTO)
                .text(post.getText())
                .build();
    }
}
