package mops.foren.domain.model;

import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Set;

@Data
public class User {
    private final String name;
    private Email email;
    private PermissionManager permissionManager;


    private Image image;

    public boolean checkPermission(ForumId id, Permission permission) {
        return this.permissionManager.checkPermission(id, permission);
    }

    public Set<ForumId> getUserForums() {
        return permissionManager.getAllForums();
    }
}

