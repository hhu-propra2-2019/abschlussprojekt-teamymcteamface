package mops.foren.domainmodel;

import java.util.EnumSet;

public class Student implements Role {

    static EnumSet<Permission> permissions = EnumSet.of(
            Permission.WRITE_THREAD,
            Permission.READ_THREAD
    );

}
