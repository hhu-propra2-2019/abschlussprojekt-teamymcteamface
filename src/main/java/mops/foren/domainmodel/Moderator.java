package mops.foren.domainmodel;

import java.util.EnumSet;

public class Moderator implements Role {

    static EnumSet<Permission> permissions = studentPermissionsAndAdditional();

    static EnumSet<Permission> studentPermissionsAndAdditional() {
        EnumSet<Permission> resultEnumSet = EnumSet.copyOf(Student.permissions);
        resultEnumSet.add(Permission.DELETE_THREAD);

        return resultEnumSet;
    }

}
