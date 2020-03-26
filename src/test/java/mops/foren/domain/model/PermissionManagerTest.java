package mops.foren.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PermissionManagerTest {

    //As these Tests relay on who has which Permissions, we define a few representable Permissions,
    //so that if the Permissions change you just have to adjust these instead of every Test

    //has to be a Permission only the Admin has
    Permission adminPermission = Permission.DELETE_TOPIC;

    //a Permission the Moderator has but the Student has not
    Permission moderatorPermission = Permission.EDIT_POST;

    //a Permission a Student has
    Permission studentPermission = Permission.READ_FORUM;

    PermissionManager permissionManager;


    @BeforeEach
    void setUp() {
        //Arrange
        this.permissionManager = new PermissionManager();
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
    void testAddForumWithPermissionAddTwoAdminsOneStudent() {
        // Arrange
        ForumId forumId1 = new ForumId(5L);
        ForumId forumId2 = new ForumId(17L);
        ForumId forumId3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(forumId2.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(forumId3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getAdmin()).containsExactly(forumId1, forumId2);
    }

    @Test
    void testAddForumWithPermissionAddNoModerator() {
        // Arrange
        ForumId forumId1 = new ForumId(5L);
        ForumId forumId2 = new ForumId(17L);
        ForumId forumId3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(forumId2.getId(), Role.ADMIN);
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
    // function in the arrage Part of the next tests.

    @Test
    void testCheckPermission_hasNoRoleInForum(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,studentPermission);

        //assert
        assertThat(hasPermission).isFalse();
    }

    @Test
    void testCheckPermission_isStudentChecksStudentPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.STUDENT);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,studentPermission);

        //assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_isStudentButChecksModeratorPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.STUDENT);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,moderatorPermission);

        //assert
        assertThat(hasPermission).isFalse();
    }

    @Test
    void testCheckPermission_isStudentButChecksAdminPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.STUDENT);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,adminPermission);

        //assert
        assertThat(hasPermission).isFalse();
    }

    @Test
    void testCheckPermission_isModeratorChecksStudentPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.MODERATOR);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,studentPermission);

        //assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_isModeratorChecksModeratorPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.MODERATOR);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,moderatorPermission);

        //assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_isModeratorButChecksAdminPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.MODERATOR);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,adminPermission);

        //assert
        assertThat(hasPermission).isFalse();
    }

    @Test
    void testCheckPermission_isAdminChecksStudentPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.ADMIN);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,studentPermission);

        //assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_isAdminChecksModeratorPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.ADMIN);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,moderatorPermission);

        //assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_isAdminChecksAdminPermission(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.ADMIN);

        //act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,adminPermission);

        //assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_NotAuthorNoGenerallRights(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.STUDENT);
        User user1 = User.builder().name("User1").build();
        User user2 = User.builder().name("User2").build();

        //Act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,
                adminPermission,user1,user2);

        // Assert
        assertThat(hasPermission).isFalse();

    }

    @Test
    void testCheckPermission_IsAuthorNoGenerallRights(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.STUDENT);
        User user1 = User.builder().name("User1").build();

        //Act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,
                adminPermission,user1,user1);

        // Assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_NotAuthorHasGenerallRights(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.ADMIN);
        User user1 = User.builder().name("User1").build();
        User user2 = User.builder().name("User2").build();

        //Act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,
                adminPermission,user1,user2);

        // Assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_IsAuthorHasGenerallRights(){
        //Arrange
        ForumId forumId1 = new ForumId(1L);
        permissionManager.addForumWithPermission(forumId1.getId(),Role.ADMIN);
        User user1 = User.builder().name("User1").build();

        //Act
        Boolean hasPermission = permissionManager.checkPermission(forumId1,
                adminPermission,user1,user1);

        // Assert
        assertThat(hasPermission).isTrue();
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
        ForumId forumId2 = new ForumId(2L);
        ForumId forumId3 = new ForumId(3L);

        //Act
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.MODERATOR);
        this.permissionManager.addForumWithPermission(forumId2.getId(), Role.MODERATOR);
        this.permissionManager.addForumWithPermission(forumId2.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(forumId3.getId(), Role.STUDENT);
        this.permissionManager.addForumWithPermission(forumId3.getId(), Role.MODERATOR);


        //Assert
        assertThat(this.permissionManager.getAllForums()).containsExactlyInAnyOrder(
                forumId1, forumId2, forumId3);
    }

    @Test
    void testGetAllForumsEmpty() {
        //Assert
        assertThat(this.permissionManager.getAllForums()).isEmpty();
    }


}
