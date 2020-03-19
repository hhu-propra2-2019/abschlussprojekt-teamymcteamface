package mops.foren.domain.model;


import lombok.NonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (Student.hasPermission(permission)) {
            return this.student.contains(id);
        }
        if (Moderator.hasPermission(permission)) {
            return this.moderator.contains(id);
        }
        if (Admin.hasPermission(permission)) {
            return this.admin.contains(id);
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

    public List<ForumId> getAllForums() {
        return this.student.stream().collect(Collectors.toList());
    }

}
