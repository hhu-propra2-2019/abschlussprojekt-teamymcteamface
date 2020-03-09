package mops.foren.model;

import java.util.HashMap;

public class User {
    String name;
    String email;
    HashMap<Long, Permission> permissons;
    Image image;
}
