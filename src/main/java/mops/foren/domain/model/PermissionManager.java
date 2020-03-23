package mops.foren.domain.model;


import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PermissionManager {

    private Set<ForumId> student;
    private Set<ForumId> moderator;
    private Set<ForumId> admin;

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

        return user.equals(author) || checkPermission(id, permission);
    }

    /**
     * Return all forumIds that the user is in.
     *
     * @return List of forum-ids.
     */
    public List<ForumId> getAllForums() {
        Set<ForumId> forumIds = new HashSet<>();
        this.student.stream().map(forumIds::add);
        this.moderator.stream().map(forumIds::add);
        this.admin.stream().map(forumIds::add);
        return new ArrayList<>(forumIds);
    }
}
