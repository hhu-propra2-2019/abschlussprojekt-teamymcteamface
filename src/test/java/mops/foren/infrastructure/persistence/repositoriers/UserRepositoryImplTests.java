package mops.foren.infrastructure.persistence.repositoriers;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Role;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IUserRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.ForumJpaRepository;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryImplTests {

    private final IUserRepository userRepositoryImpl;
    private final UserJpaRepository userJpaRepository;
    private final ForumJpaRepository forumJpaRepository;

    @Autowired
    public UserRepositoryImplTests(UserJpaRepository userJpaRepository,
                                   IUserRepository userRepositoryImpl,
                                   ForumJpaRepository forumJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.userRepositoryImpl = userRepositoryImpl;
        this.forumJpaRepository = forumJpaRepository;
    }

    @Test
    public void testCheckIfUserIsNotInDBWhenHeActuallyIsNot() {
        // Arrange
        UserDTO notInDatabaseDTO = UserDTO.builder()
                .username("notInDatabase")
                .forums(new HashSet<ForumDTO>())
                .email("trash@mail.com")
                .name("Hans Gruber")
                .posts(new ArrayList<PostDTO>())
                .roles(new HashMap<Long, Role>())
                .threads(new ArrayList<ThreadDTO>())
                .build();

        User notInDatabase = UserMapper.mapUserDtoToUser(notInDatabaseDTO);

        // Act
        Boolean isNotInDatabase =
                this.userRepositoryImpl.isUserNotInDB(notInDatabase);

        // Assert
        assertThat(isNotInDatabase).isTrue();
    }

    @Test
    public void testCheckIfUserIsNotInDBWhenHeActuallyIs() {
        // Arrange
        User userInDatabase =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user1").get());

        // Act
        Boolean isNotInDatabase =
                this.userRepositoryImpl.isUserNotInDB(userInDatabase);

        // Assert
        assertThat(isNotInDatabase).isFalse();
    }

    @Test
    public void testIfNewUserCanBeAddedToDatabase() {
        // Arrange
        UserDTO notInDatabaseDTO = UserDTO.builder()
                .username("notInDatabase")
                .forums(new HashSet<ForumDTO>())
                .email("trash@mail.com")
                .name("Hans Gruber")
                .posts(new ArrayList<PostDTO>())
                .roles(new HashMap<Long, Role>())
                .threads(new ArrayList<ThreadDTO>())
                .build();

        User notInDatabase = UserMapper.mapUserDtoToUser(notInDatabaseDTO);

        // Act
        this.userRepositoryImpl.addNewUserToDB(notInDatabase);

        // Assert
        assertThat(this.userJpaRepository.existsById(notInDatabase.getName())).isTrue();
    }

    @Test
    public void testIfUserCanBeLoadedFromDatabase() {
        // Arrange
        User userInDatabase =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user1").get());

        // Act
        User loadedUser =
                this.userRepositoryImpl.getUserFromDB(userInDatabase);

        // Assert
        assertThat(userInDatabase).isEqualTo(loadedUser);
    }

    @Test
    public void testIfForumCanBeAddedToUser() {
        // Arrange
        User userInDatabase =
                UserMapper.mapUserDtoToUser(this.userJpaRepository.findById("user1").get());

        Forum forumWithoutUser =
                ForumMapper.mapForumDtoToForum(this.forumJpaRepository.findById(1L).get());

        ForumId forumId = forumWithoutUser.getId();

        // Act
        this.userRepositoryImpl.addForumToUser(
                userInDatabase, forumWithoutUser);

        User updatedUser = UserMapper.mapUserDtoToUser(
                this.userJpaRepository.findById(userInDatabase.getName()).get());

        List<ForumId> forumsForUpdatedUser = updatedUser.getForums();

        // Assert
        assertThat(forumsForUpdatedUser).containsOnly(forumId);
    }
}
