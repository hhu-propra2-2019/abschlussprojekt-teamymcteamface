package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@Builder
public class User {
    private final String name;
    private Email email;
    private PermissionManager permissionManager;
    private List<Long> forums;

    private Image image;

    public boolean checkPermission(Id id, Permission permission) {
        return this.permissionManager.checkPermission(id, permission);
    }

    public List<Long> getUserForums() {
        return this.forums;
    }

    public boolean hasForen() {
        return forums != null;
    }
}

