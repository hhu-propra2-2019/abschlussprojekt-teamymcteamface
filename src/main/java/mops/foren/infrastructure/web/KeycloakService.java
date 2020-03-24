package mops.foren.infrastructure.web;

import mops.foren.domain.model.User;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class KeycloakService {
    /**
     * Nimmt das Authentifizierungstoken von Keycloak und erzeugt ein AccountDTO für die Views.
     *
     * @param token a Keycloak Token
     * @return neuen Account der im Template verwendet wird
     */
    public Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new Account(
                principal.getName(),
                principal.getKeycloakSecurityContext().getIdToken().getEmail(),
                null,
                token.getAccount().getRoles());
    }

    /**
     * Creates an Account from a given User.
     * @param user - User which sent the request
     * @return
     */
    public Account createAccountFromUser(User user) {
        Set<String> roles = new HashSet<>();
        roles.add("Student");

        return new Account.AccountBuilder()
                .email(user.getEmail())
                .image("")
                .name(user.getName())
                .roles(roles)
                .build();
    }
}
