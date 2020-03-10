package mops.foren.domainmodel;

import java.util.Collection;

public interface Role {

    boolean hasPermission(Permission permission);
}
