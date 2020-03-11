package mops.foren.domain.model;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.List;

public class User {
    private String name;
    private Email email;
    private HashMap<Long, Permission> permissons;
    private List<Long> forums;

    private Image image;

    public boolean checkPermission(long id) {
        throw new UnsupportedOperationException();
    }

    public List<Long> getUserForums() {
        return this.forums;
    }
}

