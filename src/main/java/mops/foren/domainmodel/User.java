package mops.foren.domainmodel;

import java.util.HashMap;

public class User {
    String name;
    String email;
    HashMap<Long, Permission> permissons;
    Image image;
}
