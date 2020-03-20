package mops.foren.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class Account {
    private final String name;
    private final String email;
    private final String image;
    private final Set<String> roles;

}
