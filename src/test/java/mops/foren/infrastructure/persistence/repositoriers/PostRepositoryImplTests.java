package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.infrastructure.persistence.mapper.PostMapper;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.PostJpaRepository;
import mops.foren.infrastructure.persistence.repositories.ThreadJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostRepositoryImplTests {

    private final IPostRepository postRepositoryImpl;
    private final ThreadJpaRepository threadJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public PostRepositoryImplTests(ThreadJpaRepository threadJpaRepository,
                                   IPostRepository postRepositoryImpl,
                                   PostJpaRepository postJpaRepository,
                                   UserJpaRepository userJpaRepository) {
        this.threadJpaRepository = threadJpaRepository;
        this.postJpaRepository = postJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.postRepositoryImpl = postRepositoryImpl;
    }

    @Test
    public void testIfPostPageCanBeLoadedForThreadFromDatabase() {
        // Arrange
        Thread threadWithTwoPosts =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());

        // Act
        PostPage loadedPage =
                this.postRepositoryImpl.getPostPageFromDB(threadWithTwoPosts.getId(), 0);

        int entryCount = loadedPage.getPosts().size();

        // Assert
        assertThat(entryCount).isEqualTo(2);
    }

    @Test
    public void testIfPostCanBeLoadedFromDatabaseById() {
        // Arrange
        Post postInDatabase =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());

        // Act
        Post loadedPost =
                PostMapper.mapPostDtoToPost(
                        this.postJpaRepository.findById(
                                postInDatabase.getId().getId()).get());

        // Assert
        assertThat(loadedPost).isEqualTo(postInDatabase);
    }

    @Test
    public void testIfPostsCanBeLoadedFromDatabaseForUser() {
        // Arrange
        User authorOfMultiplePosts =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user2").get());

        Post firstPostForUser =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(1L).get());

        Post secondPostForUser =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());

        List<Post> expectedPosts = Arrays.asList(firstPostForUser, secondPostForUser);

        // Act
        List<Post> postsForUser =
                this.postRepositoryImpl.getPostsFromUser(authorOfMultiplePosts);

        // Assert
        assertThat(postsForUser).containsOnlyElementsOf(expectedPosts);
    }

    @Test
    public void testIfAllPostsFromThreadCanBeLoadedFromDatabaseByThreadId() {
        // Arrange
        Thread threadWithTwoPosts =
                ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());

        Post firstPostInThreadWithTwoPosts =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());

        Post secondPostInThreadWithTwoPosts =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(3L).get());

        List<Post> expectedPosts = Arrays.asList(
                firstPostInThreadWithTwoPosts, secondPostInThreadWithTwoPosts);

        // Act
        List<Post> loadedPosts =
                this.postRepositoryImpl.getAllPostsByThreadId(threadWithTwoPosts.getId());

        // Assert
        assertThat(loadedPosts).containsOnlyElementsOf(expectedPosts);
    }

    @Test
    public void testIfWholeForumCanBeSearchedForContent() {
        // Arrange
        Post visiblePost =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(3L).get());

        ForumId forumToSearchIn = visiblePost.getForumId();
        String contentToSearchFor = visiblePost.getText();

        // Act
        PostPage loadedPage = this.postRepositoryImpl.searchWholeForumForContent(
                forumToSearchIn, contentToSearchFor, 0);

        List<Post> foundPosts = loadedPage.getPosts();

        // Assert
        assertThat(foundPosts).containsOnly(visiblePost);
    }

    @Test
    public void testIfPostCanBeSetToVisible() {
        // Arrange
        Post notVisiblePost =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());

        // Act
        this.postRepositoryImpl.setPostVisible(notVisiblePost.getId());

        // load updated post
        Post updatedPost = PostMapper.mapPostDtoToPost(
                this.postJpaRepository.findById(notVisiblePost.getId().getId()).get());

        Boolean isVisible = updatedPost.getVisible();

        // Assert
        assertThat(isVisible).isTrue();
    }

    @Test
    public void testIfPostCanBeDeletedFromDatabaseById() {
        // Arrange
        Post postInDatabase =
                PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());

        // Act
        this.postRepositoryImpl.deletePostById(postInDatabase.getId());

        Boolean wasDeleted =
                !this.postJpaRepository.existsById(
                        postInDatabase.getId().getId());

        // Assert
        assertThat(wasDeleted).isTrue();
    }
}


