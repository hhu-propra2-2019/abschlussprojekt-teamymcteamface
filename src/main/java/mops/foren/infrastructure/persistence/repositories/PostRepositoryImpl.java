package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.mapper.PostMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    PostJpaRepository postRepository;

    public PostRepositoryImpl(PostJpaRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getPostsFromDB(ThreadId threadId) {
        return this.postRepository.findByThread_Id(threadId.getId()).stream()
                .map(PostMapper::mapPostDtoToPost)
                .collect(Collectors.toList());
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
        List<PostDTO> postByAuthor = this.postRepository.findByAuthor_Username(user.getName());
        return postByAuthor.stream()
                .map(PostMapper::mapPostDtoToPost)
                .collect(Collectors.toList());
    }
}
