package mops.foren.domainmodel;

import java.util.HashSet;
import java.util.Set;

public class User {
    String name;
    String email;
    Set<String> roles = new HashSet<>();
    String image;
}
