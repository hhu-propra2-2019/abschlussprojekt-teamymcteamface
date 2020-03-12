package mops.foren.domain.model;

import java.util.List;

public class PermissionManager {

    private List<ForumId> moderator;
    private List<ForumId> admin;

    /**
     * Method checks if the permission requires a certain role and
     * also if the user has the required role.
     * @param id - id of the Forum in which the request was executed
     * @param permission - the event represented by it permission-type
     * @return boolean
     */
    public boolean checkPermission(Id id, Permission permission) {

        if (Moderator.hasPermission(permission)) {
            return moderator.contains(id);
        }
        if (Admin.hasPermission(permission)) {
            return admin.contains(id);
        }
        return false;

    }



}
