package mops.foren.domain.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.SetFactoryBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PermissionManagerTest {

    PermissionManager permissionManager;


    @Test
    void addForumWithPermissionAddTwoStudents() {
        // Arrange
        permissionManager = new PermissionManager();
        ForumId Id1 = new ForumId(1L);
        ForumId Id2 = new ForumId(2L);

        //Act
        permissionManager.addForumWithPermission(Id1.getId(), Role.STUDENT);
        permissionManager.addForumWithPermission(Id2.getId(),Role.STUDENT);

        //Assert
        assertThat(permissionManager.getStudent()).containsExactly(Id1,Id2);
    }

    @Test
    void addForumWithPermissionAddTwoAdminsOneStudent() {
        // Arrange
        permissionManager = new PermissionManager();
        ForumId Id1 = new ForumId(5L);
        ForumId Id2 = new ForumId(17L);
        ForumId Id3 = new ForumId(12L);

        //Act
        permissionManager.addForumWithPermission(Id1.getId(), Role.ADMIN);
        permissionManager.addForumWithPermission(Id2.getId(), Role.ADMIN);
        permissionManager.addForumWithPermission(Id3.getId(), Role.STUDENT);

        //Assert
        assertThat(permissionManager.getAdmin()).containsExactly(Id1, Id2);
    }

    @Test
    void addForumWithPermissionAddNoModerator() {
        // Arrange
        permissionManager = new PermissionManager();
        ForumId Id1 = new ForumId(5L);
        ForumId Id2 = new ForumId(17L);
        ForumId Id3 = new ForumId(12L);

        //Act
        permissionManager.addForumWithPermission(Id1.getId(), Role.ADMIN);
        permissionManager.addForumWithPermission(Id2.getId(), Role.ADMIN);
        permissionManager.addForumWithPermission(Id3.getId(), Role.STUDENT);

        //Assert
        assertThat(permissionManager.getModerator()).isEmpty();
    }

    @Test
    void addForumWithPermissionAddOneModerator() {
        // Arrange
        permissionManager = new PermissionManager();
        ForumId Id1 = new ForumId(5L);
        ForumId Id2 = new ForumId(17L);
        ForumId Id3 = new ForumId(12L);

        //Act
        permissionManager.addForumWithPermission(Id1.getId(), Role.MODERATOR);
        permissionManager.addForumWithPermission(Id2.getId(), Role.ADMIN);
        permissionManager.addForumWithPermission(Id3.getId(), Role.STUDENT);

        //Assert
        assertThat(permissionManager.getModerator()).containsExactly(Id1);
    }

    // After establishing, that the addForumWithPermission function works, we can use this function in the arrage
    // Part of the next tests.

    @Test
    void checkPermission() {

    }

    @Test
    void testCheckPermission() {
    }

    @Test
    void getAllForums() {
    }
}