package mops.foren.domain.model;

import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;


@Data
public class User {
    private final String name;
    private Email email;
    private PermissionManager permissionManager;


    private Image image;

    public Boolean checkPermission(ForumId id, Permission permission) {
        return this.permissionManager.checkPermission(id, permission);
    }

    public Boolean checkPermission(ForumId id, Permission permission,User author) {
        return this.permissionManager.checkPermission(id, permission, author, this);
    }

    public List<ForumId> getUserForums() {
        return permissionManager.getAllForums();
    }

}

