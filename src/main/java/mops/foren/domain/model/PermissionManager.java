package mops.foren.domain.model;

import java.util.Set;

public class PermissionManager {

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
    public boolean checkPermission(Id id, Permission permission) {

        if (moderator.contains(id)) {
            return Moderator.hasPermission(permission);
        }
        if (admin.contains(id)) {
            return Admin.hasPermission(permission);
        }

        return false;
    }
}
