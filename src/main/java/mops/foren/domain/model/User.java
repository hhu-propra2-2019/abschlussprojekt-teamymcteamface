package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private final String name;
    private String email;
    private PermissionManager permissionManager;
    private List<ForumId> forums;

    private Image image;

    public boolean checkPermission(Id id, Permission permission) {
        return this.permissionManager.checkPermission(id, permission);
    }

    public List<ForumId> getUserForums() {
        return this.forums;
    }

    public boolean hasForen() {
        return forums != null;
    }
}

