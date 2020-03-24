package mops.foren.domain.model;

import java.util.EnumSet;

public class Admin {

    private static final EnumSet<Permission> PERMISSIONS = EnumSet.allOf(Permission.class);

    public static boolean hasPermission(Permission permission) {
        return PERMISSIONS.contains(permission) || Moderator.hasPermission(permission);
    }
}
