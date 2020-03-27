package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.*;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.PostMapper;
import mops.foren.infrastructure.persistence.mapper.PostPageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements IPostRepository {

    private static final int PAGE_SIZE = 10;
    private static final int PAGE_SEARCH_SIZE = 20;

    private PostJpaRepository postRepository;
    private UserJpaRepository userRepository;


    public PostRepositoryImpl(PostJpaRepository postRepository, UserJpaRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostPage getPostPageFromDB(ThreadId threadId, Integer page) {
        Page<PostDTO> dtoPage = this.postRepository
                .findPostPageByThread_Id(threadId.getId(), PageRequest.of(page, PAGE_SIZE));

        return PostPageMapper.toPostPage(dtoPage, page);
    }

    /**
     * This method get a Post with the given id.
     *
     * @param postId the id of the post, which is wanted.
     * @return the wanted post.
     */
    @Override
    public Post getPostById(PostId postId) {
        PostDTO postDTO = this.postRepository.findById(postId.getId()).get();
        return PostMapper.mapPostDtoToPost(postDTO);
    }

    /**
     * This method gets all Post from an user.
     *
     * @param user the user, where the posts are wanted.
     * @return a list of all post from the user.
     */
    @Override
    public List<Post> getPostsFromUser(User user) {
        List<PostDTO> postByAuthor = this.postRepository
                .findPostListByAuthor_Username(user.getName());
        return postByAuthor.stream()
                .map(PostMapper::mapPostDtoToPost)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getAllPostsByThreadId(ThreadId id) {
        return this.postRepository.findPostDTOByThread_Id(id.getId()).stream()
                .map(PostMapper::mapPostDtoToPost)
                .collect(Collectors.toList());
    }

    @Override
    public PostPage searchWholeForumForContent(ForumId forumId, String content,
                                               Integer page) {

        Page<PostDTO> postDtoPage = this.postRepository
                .findAllByVisibleIsTrueAndForum_IdAndTextContainingIgnoreCase(
                        forumId.getId(), content, PageRequest.of(page, PAGE_SEARCH_SIZE));

        return PostPageMapper.toPostPage(postDtoPage, page);
    }

    @Override
    public void setPostVisible(PostId postId) {
        PostDTO byId = this.postRepository.findById(postId.getId()).get();
        byId.setVisible(true);
        this.postRepository.save(byId);
    }


    @Override
    public void deletePostById(PostId postId) {
        PostDTO postDTO = this.postRepository.findById(postId.getId()).get();
        if (postDTO.getVisible()) {
            UserDTO defaultDeletedUserDTO = this.userRepository.findById("Unbekannt").get();
            postDTO.setAuthor(defaultDeletedUserDTO);
            postDTO.setText("Dieser Beitrag wurde entfernt.");
            this.postRepository.save(postDTO);
        } else {
            this.postRepository.delete(postDTO);
        }
    }

}
