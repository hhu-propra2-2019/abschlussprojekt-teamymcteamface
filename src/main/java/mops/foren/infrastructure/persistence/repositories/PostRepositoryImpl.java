package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.Thread;
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
    public List<Post> getPostsFromThread(Thread thread) {
        List<PostDTO> threadDtos = postRepository.findByThread(thread.getId().getId());
        return threadDtos.stream()
                .map(PostMapper::mapPostDtoToPost)
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostById(PostId postId) {
        PostDTO postDTO = postRepository.findById(postId.getId()).get();
        return PostMapper.mapPostDtoToPost(postDTO);
    }

    @Override
    public List<Post> getPostsFromUser(User user) {
        List<PostDTO> postByAuthor = postRepository.findByAuthor(user.getName());
        return postByAuthor.stream()
                .map(PostMapper::mapPostDtoToPost)
                .collect(Collectors.toList());
    }
}
