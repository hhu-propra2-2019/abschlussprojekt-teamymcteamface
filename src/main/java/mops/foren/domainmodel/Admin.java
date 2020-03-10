package mops.foren.domainmodel;

import java.util.Collection;
import java.util.EnumSet;

public class Admin implements Role {

    static EnumSet<Permission> permissions = EnumSet.allOf(Permission.class);

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }
}
