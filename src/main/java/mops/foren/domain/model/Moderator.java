package mops.foren.domain.model;

import java.util.EnumSet;

public class Moderator {
    private static final EnumSet<Permission> PERMISSIONS = EnumSet.of(
            Permission.EDIT_POST,
            Permission.DELETE_THREAD,
            Permission.EDIT_TOPIC,
            Permission.DELETE_POST
    );

    public static boolean hasPermission(Permission permission) {
        return PERMISSIONS.contains(permission) || Student.hasPermission(permission);
    }
}
