package mops.foren.infrastructure.web.controller;

import mops.foren.applicationservices.UserService;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.web.Account;
import mops.foren.infrastructure.web.KeycloakService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;

@Controller
@SessionScope
@RequestMapping("/foren")
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class DashboardController {

    private UserService userService;
    private KeycloakService keycloakService;

    public DashboardController(UserService userService, KeycloakService keycloakService) {
        this.userService = userService;
        this.keycloakService = keycloakService;
    }

    @GetMapping
    public String main() {
        return "index";
    }

    /**
     * Adds the account object to each request.
     * Image and roles have to be added in the future.
     * @param token - KeycloakAuthenficationToken
     * @return
     */
    @ModelAttribute("account")
    public Account addAccountToTheRequest(KeycloakAuthenticationToken token) {
        if (token == null) {
            return null;
        }
        User user = this.userService.getUserFromDB(token);

        return this.keycloakService.createAccountFromUser(user);
    }
}
