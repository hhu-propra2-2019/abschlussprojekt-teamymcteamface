package mops.foren.domainmodel;

import java.util.Collection;
import java.util.EnumSet;

public class Student implements Role {

    static EnumSet<Permission> permissions = EnumSet.of(
            Permission.WRITE_THREAD,
            Permission.READ_THREAD
    );

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public boolean hasPermissions(Collection<Permission> permissions) {
        return this.permissions.containsAll(permissions);
    }

}
