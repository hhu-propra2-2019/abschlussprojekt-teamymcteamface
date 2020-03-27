package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Role;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.ForumJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryImplTests {

    /**
     * Repository under test.
     */
    private final UserRepositoryImpl userRepositoryImpl;

    /**
     * Jpa user repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final UserJpaRepository userJpaRepository;

    /**
     * Jpa forum repository that can be assumed to work correctly. Used for database setup before
     * the actual tests.
     */
    private final ForumJpaRepository forumJpaRepository;

    private User userInDatabase;
    private User userNotInDatabase;
    private Forum forumWithoutUser;

    /**
     * Constructor for repo injection.
     *
     * @param userJpaRepository  injected userJpaRepository
     * @param userRepositoryImpl injected userRepositoryImpl
     * @param forumJpaRepository injected forumJpaRepository
     */
    @Autowired
    public UserRepositoryImplTests(UserJpaRepository userJpaRepository,
                                   UserRepositoryImpl userRepositoryImpl,
                                   ForumJpaRepository forumJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.userRepositoryImpl = userRepositoryImpl;
        this.forumJpaRepository = forumJpaRepository;
    }

    /**
     * Set up method with injection. SetsUp the tests.
     */
    @BeforeEach
    public void setUp() {

        UserDTO notInDatabaseDTO = UserDTO.builder()
                .username("notInDatabase")
                .forums(new HashSet<ForumDTO>())
                .email("trash@mail.com")
                .name("asdfghjkl")
                .posts(new ArrayList<PostDTO>())
                .roles(new HashMap<Long, Role>())
                .threads(new ArrayList<ThreadDTO>())
                .build();

        this.userInDatabase = UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user1").get());
        this.userNotInDatabase = UserMapper.mapUserDtoToUser(notInDatabaseDTO);
        this.forumWithoutUser = ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(1L).get());
    }

    @Test
    public void testCheckIfUserIsNotInDBWhenHeActuallyIsNot() {
        // Act
        Boolean isNotInDatabase = this.userRepositoryImpl.isUserNotInDB(this.userNotInDatabase);
        //Assert
        assertThat(isNotInDatabase).isTrue();
    }

    @Test
    public void testCheckIfUserIsNotInDBWhenHeActuallyIs() {
        // Act
        Boolean isNotInDatabase = this.userRepositoryImpl.isUserNotInDB(this.userInDatabase);
        //Assert
        assertThat(isNotInDatabase).isFalse();
    }

    @Test
    public void testIfNewUserCanBeAddedToDatabase() {
        // Act
        this.userRepositoryImpl.addNewUserToDB(this.userNotInDatabase);
        // Assert
        assertThat(this.userJpaRepository.existsById(this.userNotInDatabase.getName())).isTrue();
    }

    @Test
    public void testIfUserCanBeLoadedFromDatabase() {
        // Act
        User loadedUser = this.userRepositoryImpl.getUserFromDB(this.userInDatabase);
        // Assert
        assertThat(this.userInDatabase).isEqualTo(loadedUser);
    }

    @Test
    public void testIfForumCanBeAddedToUser() {
        // Arrange
        List<ForumId> expectedForums = Arrays.asList(this.forumWithoutUser.getId());
        // Act
        this.userRepositoryImpl.addForumToUser(
                this.userInDatabase, this.forumWithoutUser);
        // Reload User1 and obtain List for check
        User updatedUser = UserMapper.mapUserDtoToUser(
                this.userJpaRepository.findById("user1").get());
        List<ForumId> forumsForUpdatedUser = updatedUser.getForums();
        // Assert
        assertThat(forumsForUpdatedUser).isEqualTo(expectedForums);
    }
}
