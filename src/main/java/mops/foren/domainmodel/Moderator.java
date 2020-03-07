package mops.foren.domainmodel;

import java.util.Collection;
import java.util.EnumSet;

public class Moderator implements Role {

    static EnumSet<Permission> permissions = studentPermissionsAndAdditional();

    static EnumSet<Permission> studentPermissionsAndAdditional() {
        EnumSet<Permission> resultEnumSet = EnumSet.copyOf(Student.permissions);
        resultEnumSet.add(Permission.DELETE_THREAD);

        return resultEnumSet;
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public boolean hasPermissions(Collection<Permission> permissions) {
        return this.permissions.containsAll(permissions);
    }

}
