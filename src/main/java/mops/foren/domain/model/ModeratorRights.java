package mops.foren.domain.model;

import java.util.HashSet;
import java.util.Set;

public class ModeratorRights {
    private static final HashSet<Permission> permissions = new HashSet<>(){{
        add(Permission.CREATE_POST);
        add(Permission.READ_THREAD);
        add(Permission.CREATE_THREAD);
    }};

    public static boolean hasPermission(Permission permission){
        return permissions.contains(permission);
    }
}
