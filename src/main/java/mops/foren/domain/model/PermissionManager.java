package mops.foren.domain.model;


import java.util.Set;

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
     * @return boolean
     */
    public boolean checkPermission(ForumId id, Permission permission) {
        if (Student.hasPermission(permission)) {
            return student.contains(id);
        }
        if (Moderator.hasPermission(permission)) {
            return moderator.contains(id);
        }
        if (Admin.hasPermission(permission)) {
            return admin.contains(id);
        }
        return false;
    }

    public Set<ForumId> getAllForums() {
        return student;
    }

}
