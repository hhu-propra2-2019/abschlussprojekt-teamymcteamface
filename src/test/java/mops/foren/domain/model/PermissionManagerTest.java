package mops.foren.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

class PermissionManagerTest {

    //As these Tests relay on who has which Permissions, we define a few representable Permissions,
    //so that if the Permissions change you just have to adjust these instead of every Test

    //has to be a Permission only the Admin has
    EnumSet<Permission> allRights = EnumSet.allOf(Permission.class);

    PermissionManager permissionManager;


    @BeforeEach
    void setUp() {
        //Arrange
        this.permissionManager = new PermissionManager();
    }

    @Test
    void testGetAllForumsEmpty() {
        //Assert
        assertThat(this.permissionManager.getAllForums()).isEmpty();
    }

    @Test
    void testAddForumWithPermissionAddStudent() {
        // Arrange
        ForumId forumId1 = new ForumId(1L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getStudent()).containsExactly(forumId1);
    }

    @Test
    void testAddForumWithPermissionAddTwoStudents() {
        // Arrange
        ForumId forumId1 = new ForumId(1L);
        ForumId forumId2 = new ForumId(2L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);
        this.permissionManager.addForumWithPermission(forumId2.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getStudent()).containsExactly(forumId1, forumId2);
    }

    @Test
    void testAddForumWithPermissionOneAdminAndOneStudent() {
        // Arrange
        ForumId forumId1 = new ForumId(5L);
        ForumId forumId3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(forumId3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getAdmin()).containsExactly(forumId1);
    }

    @Test
    void testAddForumWithPermissionAddNoModerator() {
        // Arrange
        ForumId forumId1 = new ForumId(5L);
        ForumId forumId3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(forumId3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getModerator()).isEmpty();
    }

    @Test
    void testAddForumWithPermissionAddOneModerator() {
        // Arrange
        ForumId forumId1 = new ForumId(5L);
        ForumId forumId2 = new ForumId(17L);
        ForumId forumId3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.MODERATOR);
        this.permissionManager.addForumWithPermission(forumId2.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(forumId3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getModerator()).containsExactly(forumId1);
    }

    // After establishing, that the addForumWithPermission function works, we can use this
    // function in the arrange-Part of the next tests.

    @Test
    void testCheckPermission_hasNoRoleInForum() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        //assert
        allRights.forEach(permission ->
                assertThat(permissionManager.checkPermission(forumId1, permission)).isFalse());
    }

    @Test
    void testCheckPermission_isStudentChecksStudentPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);

        //assert
        allRights.stream().filter(Student::hasPermission)
                .forEach(permission -> assertThat(this.permissionManager
                        .checkPermission(forumId1, permission)).isTrue());
    }

    @Test
    void testCheckPermission_isStudentChecksOtherPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);

        //assert
        allRights.stream().filter(permission -> !Student.hasPermission(permission))
                .forEach(permission -> assertThat(this.permissionManager
                        .checkPermission(forumId1, permission)).isFalse());
    }

    @Test
    void testCheckPermission_isModeratorChecksModeratorPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.MODERATOR);

        //assert
        allRights.stream().filter(Moderator::hasPermission)
                .forEach(permission -> assertThat(this.permissionManager
                        .checkPermission(forumId1, permission)).isTrue());
    }

    @Test
    void testCheckPermission_isModeratorChecksOtherPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.MODERATOR);

        //assert
        allRights.stream().filter(permission -> !Moderator.hasPermission(permission))
                .forEach(permission -> assertThat(this.permissionManager
                        .checkPermission(forumId1, permission)).isFalse());
    }

    @Test
    void testCheckPermission_isAdminChecksAdminPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);

        //assert
        allRights.forEach(permission -> assertThat(this.permissionManager
                .checkPermission(forumId1, permission)).isTrue());
    }


    @Test
    void testCheckPermission_NotAuthorNoGeneralRights() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);
        User user1 = User.builder().name("User1").build();
        User user2 = User.builder().name("User2").build();

        //assert
        allRights.stream().filter(permission -> !Student.hasPermission(permission))
                .forEach(permission -> assertThat(this.permissionManager
                        .checkPermission(forumId1, permission, user1, user2)).isFalse());

    }

    @Test
    void testCheckPermission_IsAuthorNoGeneralRights() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);
        User user1 = User.builder().name("User1").build();

        //Assert
        allRights.stream().filter(permission -> !Student.hasPermission(permission))
                .forEach(permission -> assertThat(this.permissionManager
                        .checkPermission(forumId1, permission, user1, user1)).isTrue());
    }

    @Test
    void testCheckPermission_NotAuthorHasGeneralRights() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);
        User user1 = User.builder().name("User1").build();
        User user2 = User.builder().name("User2").build();

        //Assert
        allRights.forEach(permission -> assertThat(this.permissionManager
                .checkPermission(forumId1, permission, user1, user2)).isTrue());
    }

    @Test
    void testCheckPermission_IsAuthorHasGeneralRights() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);
        User user1 = User.builder().name("User1").build();

        //Assert
        allRights.forEach(permission -> assertThat(this.permissionManager
                .checkPermission(forumId1, permission, user1, user1)).isTrue());
    }

    @Test
    void testGetAllForumsOnlyStudents() {
        //Arrange

        ForumId forumId1 = new ForumId(1L);
        ForumId forumId2 = new ForumId(2L);
        ForumId forumId3 = new ForumId(3L);

        //Act

        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);
        this.permissionManager.addForumWithPermission(forumId2.getId(), Role.STUDENT);
        this.permissionManager.addForumWithPermission(forumId3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getAllForums()).containsExactlyInAnyOrder(
                forumId1, forumId2, forumId3);
    }

    @Test
    void testGetAllForumsDuplicate() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.MODERATOR);

        //Assert
        assertThat(this.permissionManager.getAllForums()).containsExactly(forumId1);
    }
}
