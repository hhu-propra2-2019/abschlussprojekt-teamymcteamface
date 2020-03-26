package mops.foren.domain.model;


import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Value
public class PermissionManager {

    private Set<ForumId> student = new HashSet<>();
    private Set<ForumId> moderator = new HashSet<>();
    private Set<ForumId> admin = new HashSet<>();

    /**
     * Method checks if the permission requires a certain role and
     * also if the user has the required role.
     *
     * @param id         - id of the Forum in which the request was executed
     * @param permission - the event represented by it permission-type
     * @return Boolean
     */
    public Boolean checkPermission(ForumId id, Permission permission) {
        if (this.admin.contains(id)) {
            return Admin.hasPermission(permission);
        }
        if (this.moderator.contains(id)) {
            return Moderator.hasPermission(permission);
        }
        if (this.student.contains(id)) {
            return Student.hasPermission(permission);
        }
        return false;
    }

    /**
     * Method checks if the permission requires a certain role and
     * also if the user has the required role or has the Rights because he is the Author.
     *
     * @param id         - id of the Forum in which the request was executed
     * @param permission - the event represented by it permission-type
     * @param author     - author of the Object on which the permission is to check
     * @param user       - user that is trying to execute an operation which needs permission
     * @return Boolean
     */
    public Boolean checkPermission(ForumId id, Permission permission,
                                   @NonNull User author, @NonNull User user) {

        return user.getName().equals(author.getName()) || checkPermission(id, permission);
    }

    /**
     * Return all forumIds that the user is in.
     *
     * @return List of forum-ids.
     */
    public List<ForumId> getAllForums() {
        Set<ForumId> forumIds = new HashSet<>();
        forumIds.addAll(this.student);
        forumIds.addAll(this.moderator);
        forumIds.addAll(this.admin);
        return new ArrayList<>(forumIds);
    }

    /**
     * Add forum to role list of user.
     *
     * @param forumId Forum-id, where user has this role
     * @param role    The role, the user has in the forum.
     */
    public void addForumWithPermission(Long forumId, Role role) {
        if (role.equals(Role.ADMIN)) {
            this.admin.add(new ForumId(forumId));
        } else if (role.equals(Role.MODERATOR)) {
            this.moderator.add(new ForumId(forumId));
        } else if (role.equals(Role.STUDENT)) {
            this.student.add(new ForumId(forumId));
        }
    }
}
