package mops.foren.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PermissionManagerTest {

    PermissionManager permissionManager;


    @BeforeEach
    void setUp() {
        //Arrange
        this.permissionManager = new PermissionManager();
    }

    @Test
    void addForumWithPermissionAddTwoStudents() {
        // Arrange
        ForumId Id1 = new ForumId(1L);
        ForumId Id2 = new ForumId(2L);

        //Act
        this.permissionManager.addForumWithPermission(Id1.getId(), Role.STUDENT);
        this.permissionManager.addForumWithPermission(Id2.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getStudent()).containsExactly(Id1, Id2);
    }

    @Test
    void addForumWithPermissionAddTwoAdminsOneStudent() {
        // Arrange
        ForumId Id1 = new ForumId(5L);
        ForumId Id2 = new ForumId(17L);
        ForumId Id3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(Id1.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(Id2.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(Id3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getAdmin()).containsExactly(Id1, Id2);
    }

    @Test
    void addForumWithPermissionAddNoModerator() {
        // Arrange
        ForumId Id1 = new ForumId(5L);
        ForumId Id2 = new ForumId(17L);
        ForumId Id3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(Id1.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(Id2.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(Id3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getModerator()).isEmpty();
    }

    @Test
    void addForumWithPermissionAddOneModerator() {
        // Arrange
        ForumId Id1 = new ForumId(5L);
        ForumId Id2 = new ForumId(17L);
        ForumId Id3 = new ForumId(12L);

        //Act
        this.permissionManager.addForumWithPermission(Id1.getId(), Role.MODERATOR);
        this.permissionManager.addForumWithPermission(Id2.getId(), Role.ADMIN);
        this.permissionManager.addForumWithPermission(Id3.getId(), Role.STUDENT);

        //Assert
        assertThat(this.permissionManager.getModerator()).containsExactly(Id1);
    }

    // After establishing, that the addForumWithPermission function works, we can use this function in the arrage
    // Part of the next tests.

    @Test
    void checkPermission() {

    }


    //Ab hier die gleiche Methode mit der erweiterten Signatur!
    @Test
    void testCheckPermission_UserIsAuthorButNoSpecialPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);

        User sebastian = User.builder().name("Sebastian").build();

        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);

        //Act
        Boolean hasPermission = this.permissionManager.checkPermission(
                forumId1, Permission.DELETE_POST, sebastian, sebastian);


        //Assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_UserIsAuthorAndAndHasSpecialPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);

        User valentin = User.builder().name("Valentin").build();
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);

        //Act
        Boolean hasPermission = this.permissionManager.checkPermission(
                forumId1, Permission.DELETE_POST, valentin, valentin);

        //Assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_UserIsNotAuthorAndButHasSpecialPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);

        User sebastian = User.builder().name("Sebastian").build();
        User valentin = User.builder().name("Valentin").build();
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.ADMIN);

        //Act
        Boolean hasPermission = this.permissionManager.checkPermission(
                forumId1, Permission.DELETE_POST, sebastian, valentin);

        //Assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void testCheckPermission_UserIsNotAuthorAndNoSpecialPermission() {
        //Arrange
        ForumId forumId1 = new ForumId(1L);

        User sebastian = User.builder().name("Sebastian").build();
        User valentin = User.builder().name("Valentin").build();
        this.permissionManager.addForumWithPermission(forumId1.getId(), Role.STUDENT);

        //Act
        Boolean hasPermission = this.permissionManager.checkPermission(
                forumId1, Permission.DELETE_POST, sebastian, valentin);

        //Assert
        assertThat(hasPermission).isFalse();
    }


    @Test
    void getAllForumsOnlyStudents() {
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
    void getAllForumsDuplicate() {
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
    void getAllForumsEmpty() {
        //Assert
        assertThat(this.permissionManager.getAllForums()).isEmpty();
    }


}