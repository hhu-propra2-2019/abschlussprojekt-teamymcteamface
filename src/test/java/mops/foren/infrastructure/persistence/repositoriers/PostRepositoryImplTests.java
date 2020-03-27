package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.User;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.persistence.mapper.PostMapper;
import mops.foren.infrastructure.persistence.mapper.ThreadMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.*;
import org.junit.jupiter.api.BeforeEach;
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

    /**
     * Repository under test.
     */
    private final PostRepositoryImpl postRepositoryImpl;

    /**
     * Jpa thread repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final ThreadJpaRepository threadJpaRepository;

    /**
     * Jpa topic repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final TopicJpaRepository topicJpaRepository;

    /**
     * Jpa post repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final PostJpaRepository postJpaRepository;


    /**
     * Jpa user repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final UserJpaRepository userJpaRepository;

    private Thread threadWithTwoPosts;
    private Post firstPostInThreadWithTwoPosts;
    private Post secondPostInThreadWithTwoPosts;
    private User authorOfMultiplePosts;
    private Post firstPostForUser;
    private Post secondPostForUser;
    private Post notVisiblePost;
    private Post visiblePost;

    /**
     * Constructor for repo injection.
     *
     * @param threadJpaRepository injected threadJpaRepository
     * @param postRepositoryImpl  injected postRepositoryImpl
     * @param topicJpaRepository  injected topicJpaRepository
     * @param postJpaRepository   injected postJpaRepository
     * @param userJpaRepository   injected userJpaRepository
     */
    @Autowired
    public PostRepositoryImplTests(ThreadJpaRepository threadJpaRepository,
                                   PostRepositoryImpl postRepositoryImpl,
                                   TopicJpaRepository topicJpaRepository,
                                   PostJpaRepository postJpaRepository,
                                   UserJpaRepository userJpaRepository) {
        this.threadJpaRepository = threadJpaRepository;
        this.topicJpaRepository = topicJpaRepository;
        this.postJpaRepository = postJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.postRepositoryImpl = postRepositoryImpl;
    }

    /**
     * Set up method with injection. SetsUp the tests.
     */
    @BeforeEach
    public void setUp() {

        this.threadWithTwoPosts = ThreadMapper.mapThreadDtoToThread(this.threadJpaRepository.findById(2L).get());
        this.firstPostInThreadWithTwoPosts = PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());
        this.secondPostInThreadWithTwoPosts = PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(3L).get());
        this.authorOfMultiplePosts = UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user2").get());
        this.firstPostForUser = PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(1L).get());
        this.secondPostForUser = PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());
        this.notVisiblePost = PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(2L).get());
        this.visiblePost = PostMapper.mapPostDtoToPost(this.postJpaRepository.findById(3L).get());
    }

    @Test
    public void testIfPostPageCanBeLoadedForThreadFromDatabase() {
        // Act
        PostPage loadedPage = this.postRepositoryImpl.getPostPageFromDB(this.threadWithTwoPosts.getId(), 0);
        int entryCount = loadedPage.getPosts().size();

        // Assert
        assertThat(entryCount).isEqualTo(2);
    }

    @Test
    public void testIfPostCanBeLoadedFromDatabaseById() {
        // Act
        Post loadedPost = PostMapper.mapPostDtoToPost(
                this.postJpaRepository.findById(this.firstPostInThreadWithTwoPosts.getId().getId()).get());
        // Assert
        assertThat(loadedPost).isEqualTo(this.firstPostInThreadWithTwoPosts);
    }

    @Test
    public void testIfPostsCanBeLoadedFromDatabaseForUser() {
        // Arrange
        List<Post> expectedPosts = Arrays.asList(this.firstPostForUser, this.secondPostForUser);
        // Act
        List<Post> postsForUser = this.postRepositoryImpl.getPostsFromUser(this.authorOfMultiplePosts);
        // Assert
        assertThat(postsForUser).containsOnlyElementsOf(expectedPosts);
    }

    @Test
    public void testIfAllPostsFromThreadCanBeLoadedFromDatabaseByThreadId() {
        // Arrange
        List<Post> expectedPosts = Arrays.asList(this.firstPostInThreadWithTwoPosts, this.secondPostInThreadWithTwoPosts);
        // Act
        List<Post> loadedPosts = this.postRepositoryImpl.getAllPostsByThreadId(this.threadWithTwoPosts.getId());
        // Assert
        assertThat(loadedPosts).containsOnlyElementsOf(expectedPosts);
    }

    @Test
    public void testIfWholeForumCanBeSearchedForContent() {
        // Arrange
        ForumId forumToSearchIn = this.visiblePost.getForumId();
        String contentToSearchFor = this.visiblePost.getText();

        // Act
        PostPage loadedPage = this.postRepositoryImpl.searchWholeForumForContent(
                forumToSearchIn, contentToSearchFor, 0);
        List<Post> foundPosts = loadedPage.getPosts();
        // Assert
        assertThat(foundPosts).containsOnly(this.visiblePost);
    }

    @Test
    public void testIfPostCanBeSetToVisible() {
        // Act
        this.postRepositoryImpl.setPostVisible(this.notVisiblePost.getId());
        // load updated post
        Post updatedPost = PostMapper.mapPostDtoToPost(
                this.postJpaRepository.findById(this.notVisiblePost.getId().getId()).get());
        Boolean isVisible = updatedPost.getVisible();
        // Assert
        assertThat(isVisible).isTrue();
    }

    @Test
    public void testIfPostCanBeDeletedFromDatabaseById() {
        // Act
        this.postRepositoryImpl.deletePostById(this.firstPostInThreadWithTwoPosts.getId());
        Boolean wasDeleted = !this.postJpaRepository.existsById(this.firstPostInThreadWithTwoPosts.getId().getId());
        // Assert
        assertThat(wasDeleted).isTrue();
    }
}


