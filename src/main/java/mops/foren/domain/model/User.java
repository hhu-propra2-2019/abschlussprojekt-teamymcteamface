package mops.foren.domain.model;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.List;

public class User {
    String name;
    Email email;
    HashMap<Long, Permission> permissons;
    List<Forum> forums;
    List<Post> posts;
    Image image;

    Post getNewestPost() {
        throw new UnsupportedOperationException();
    }

    Thread getNewestResponse() {
        throw new UnsupportedOperationException();
    }

}

