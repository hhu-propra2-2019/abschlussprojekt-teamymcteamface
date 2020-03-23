package mops.foren.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class Account {

    // no check for size because this is not our responsibility
    @NotNull(message = "Name cannot be Null.")
    private final String name;

    // no check for size because this is not our responsibility
    @NotNull(message = "Email cannot be Null.")
    private final String email;

    // no checks because image is Null (currently)
    private final String image;

    @NotEmpty(message = "Name cannot be empty.")
    private final Set<String> roles;

}
