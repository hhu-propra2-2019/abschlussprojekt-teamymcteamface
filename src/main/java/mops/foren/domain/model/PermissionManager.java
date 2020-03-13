package mops.foren.domain.model;


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
    public Boolean checkPermission(ForumId id, Permission permission, User author, User user) {
        Boolean check = false;
        if (OwnRights.hasPermission(permission)) {
            check = author.equals(user);
        }

        check = checkPermission(id,permission);
        return check;
    }

    public List<ForumId> getAllForums() {
        return student.stream().collect(Collectors.toList());
    }

}
