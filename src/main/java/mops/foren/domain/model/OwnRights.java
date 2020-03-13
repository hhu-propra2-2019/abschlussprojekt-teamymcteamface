package mops.foren.domain.model;

import java.util.EnumSet;

public class OwnRights {
    private static final EnumSet<Permission> PERMISSIONS = EnumSet.of(
            Permission.DELETE_POST,
            Permission.EDIT_POST,
            Permission.DELETE_THREAD
    );

    public static boolean hasPermission(Permission permission) {
        return PERMISSIONS.contains(permission);
    }
}

