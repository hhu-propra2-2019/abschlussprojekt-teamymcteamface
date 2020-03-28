package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadMapperTests {

    private ThreadDTO threadDTO;
    private UserDTO authorDTO;

    /**
     * Sets up the test environment for ThreadMapper.
     */
    @BeforeEach
    public void setUp() {
        TopicId topicId = new TopicId(2L);

        TopicDTO topicDTO = TopicDTO.builder() // only Id is needed
                .id(topicId.getId())
                .build();

        this.authorDTO = UserDTO.builder()
                .username("stuentin")
                .email("studentin@email.com")
                .name("Anna")
                .forums(new HashSet<>())
                .posts(new LinkedList<>())
                .threads(new LinkedList<>())
                .build();

        ForumDTO forumDTO = ForumDTO.builder()
                .id(10L)
                .build();

        this.threadDTO = ThreadDTO.builder()
                .id(1L)
                .forum(forumDTO)
                .author(this.authorDTO)
                .topic(topicDTO)
                .title("thread title")
                .description("thread description")
                .anonymous(true)
                .moderated(false)
                .posts(new LinkedList<>())
                .visible(true)
                .build();
    }

    @Test
    public void testTitleIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        String title = ThreadMapper.mapThreadDtoToThread(this.threadDTO).getTitle();

        // Assert
        assertThat(title).isEqualTo(this.threadDTO.getTitle());
    }

    @Test
    public void testDescriptionIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        String description = ThreadMapper.mapThreadDtoToThread(this.threadDTO).getDescription();

        // Assert
        assertThat(description).isEqualTo(this.threadDTO.getDescription());
    }

    @Test
    public void testIdIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        Long threadId = ThreadMapper.mapThreadDtoToThread(this.threadDTO).getId().getId();

        // Assert
        assertThat(threadId).isEqualTo(this.threadDTO.getId());
    }

    @Test
    public void testTopicIdIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        TopicId topicId = ThreadMapper.mapThreadDtoToThread(this.threadDTO).getTopicId();

        // Assert
        assertThat(topicId.getId()).isEqualTo(this.threadDTO.getTopic().getId());
    }

    @Test
    public void testAuthorIsCorrectlyMappedFromThreadDTOToModel() {
        // Arrange
        User userFromArrange = UserMapper.mapUserDtoToUser(this.authorDTO);

        // Act
        User author = ThreadMapper.mapThreadDtoToThread(this.threadDTO).getAuthor();

        // Assert
        assertThat(author).isEqualTo(userFromArrange);
    }

    @Test
    public void testForumIdIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        ForumId forumId = ThreadMapper.mapThreadDtoToThread(this.threadDTO)
                .getForumId();

        // Assert
        assertThat(forumId.getId()).isEqualTo(10L);
    }

    @Test
    public void testAnonymIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        Boolean anonymous = ThreadMapper.mapThreadDtoToThread(this.threadDTO)
                .getAnonymous();

        // Assert
        assertThat(anonymous).isTrue();
    }

    @Test
    public void testVisibleIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        Boolean visible = ThreadMapper.mapThreadDtoToThread(this.threadDTO)
                .getVisible();

        // Assert
        assertThat(visible).isTrue();
    }

    @Test
    public void testModeratedIsCorrectlyMappedFromThreadDTOToModel() {
        // Act
        Boolean moderated = ThreadMapper.mapThreadDtoToThread(this.threadDTO)
                .getModerated();

        // Assert
        assertThat(moderated).isFalse();
    }

    @Test
    public void testUnModeratedIsZeroIfThreadIsUnmoderated() {
        // Arrange
        this.threadDTO.setPosts(List.of(getPost(true)));
        this.threadDTO.setModerated(false);
        // Act
        Thread thread = ThreadMapper.mapThreadDtoToThread(this.threadDTO);

        // Assert
        assertThat(thread.getUnModerated()).isEqualTo(0L);
    }

    @Test
    public void testUnModeratedIsZeroIfAllPostsAreVisible() {
        // Arrange
        PostDTO post1 = getPost(true);
        PostDTO post2 = getPost(true);
        this.threadDTO.setPosts(List.of(post1, post2));
        this.threadDTO.setModerated(true);
        // Act
        Thread thread = ThreadMapper.mapThreadDtoToThread(this.threadDTO);

        // Assert
        assertThat(thread.getUnModerated()).isEqualTo(0L);
    }


    @Test
    public void testUnModeratedIsOneIfOnePostIsVisible() {
        // Arrange
        PostDTO post1 = getPost(true);
        PostDTO post2 = getPost(false);
        this.threadDTO.setPosts(List.of(post1, post2));
        this.threadDTO.setModerated(true);
        // Act
        Thread thread = ThreadMapper.mapThreadDtoToThread(this.threadDTO);

        // Assert
        assertThat(thread.getUnModerated()).isEqualTo(1L);
    }

    private PostDTO getPost(Boolean visible) {
        return PostDTO.builder()
                .visible(visible)
                .dateTime(LocalDateTime.now())
                .build();
    }

}
