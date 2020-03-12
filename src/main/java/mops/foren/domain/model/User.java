package mops.foren.domain.model;

import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;

@Data
public class User {
    private final String name;
    private Email email;
    private PermissionManager permissionManager;
    private List<ForumId> forums;

    private Image image;

    public boolean checkPermission(Id id, Permission permission) {
        return this.permissionManager.checkPermission(id, permission);
    }

    public List<ForumId> getUserForums() {
        return this.forums;
    }
}

