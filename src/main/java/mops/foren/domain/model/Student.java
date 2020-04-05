package mops.foren.domain.model;

import java.util.EnumSet;

public class Student {

    private static final EnumSet<Permission> PERMISSIONS = EnumSet.of(
            Permission.CREATE_POST,
            Permission.READ_THREAD,
            Permission.CREATE_THREAD,
            Permission.READ_FORUM,
            Permission.READ_TOPIC
    );

    public static boolean hasPermission(Permission permission) {
        return PERMISSIONS.contains(permission);
    }
}
