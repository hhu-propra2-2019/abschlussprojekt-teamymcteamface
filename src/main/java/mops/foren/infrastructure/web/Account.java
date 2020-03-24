package mops.foren.infrastructure.web;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Account {
    private final String name;
    private final String email;
    private final String image;
    private final Set<String> roles;

}
