package mops.foren;

import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.Set;

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

    private User user1;
    private User user2;

    @Autowired
    public UserRepositoryImplTests(UserJpaRepository userJpaRepository,
                                   UserRepositoryImpl userRepositoryImpl) {
        this.userJpaRepository = userJpaRepository;

        this.userRepositoryImpl = userRepositoryImpl;
    }

    /**
     * Set up method with injection. SetsUp the tests.
     */
    @BeforeEach
    public void setUp() {

        Set<ForumDTO> setWithoutForumDTO = new HashSet<>();

        UserDTO user1DTO = UserDTO.builder()
                .username("user1")
                .email("user1@hhu.de")
                .forums(setWithoutForumDTO)
                .build();

        UserDTO user2DTO = UserDTO.builder()
                .username("user2")
                .email("user2@hhu.de")
                .forums(setWithoutForumDTO)
                .build();

        this.userJpaRepository.save(user1DTO);

        this.user1 = UserMapper.mapUserDtoToUser(user1DTO);
        this.user2 = UserMapper.mapUserDtoToUser(user2DTO);
    }

    @Test
    public void testCheckIfUserIsNotInDBWhenHeActuallyIsNot() {
        // Act
        Boolean isNotInDatabase = this.userRepositoryImpl.isUserNotInDB(this.user2);
        //Assert
        assertThat(isNotInDatabase).isTrue();
    }

    @Test
    public void testCheckIfUserIsNotInDBWhenHeActuallyIs() {
        // Act
        Boolean isNotInDatabase = this.userRepositoryImpl.isUserNotInDB(this.user1);
        //Assert
        assertThat(isNotInDatabase).isFalse();
    }

    @Test
    public void testIfNewUserCanBeAddedToDatabase() {
        // Act
        this.userRepositoryImpl.addNewUserToDB(this.user2);
        // Assert
        assertThat(this.userJpaRepository.existsById(this.user2.getName())).isTrue();
    }

    @Test
    public void testIfUserCanBeLoadedFromDatabase() {
        // Act
        User loadedUser = this.userRepositoryImpl.getUserFromDB(this.user1);
        // Assert
        assertThat(this.user1).isEqualTo(loadedUser);
    }

    /*
    @Test
    public void testIfForumCanBeAddedToUser() {
        // Arrange
        ForumDTO forum2DTO = ForumDTO.builder()
                .id(2L)
                .title("forum2")
                .description("description2")
                .build();

        Forum forum2 = ForumMapper.mapForumDtoToForum(forum2DTO);
        // Act
        this.userRepositoryImpl.addForumToUser(this.user1, forum2);
        // Reload User1
        User updatedUser1 = UserMapper.mapUserDtoToUser(
        this.userJpaRepository.findById(this.user1.getName()).get());
        // Assert
        assertThat(updatedUser1.getForums()).contains(forum2.getId());
    }
     */
}
