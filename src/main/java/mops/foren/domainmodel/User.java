package mops.foren.domainmodel;

import java.util.Collection;

public class User {
    String name;
    String email;
    Role role;
    Image image;

    public boolean hasPermission(Permission permission) {
        return role.hasPermission(permission);
    }
}
