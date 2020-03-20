package mops.foren.infrastructure.web;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

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
}
