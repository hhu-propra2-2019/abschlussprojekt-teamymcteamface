package mops.foren;

import mops.foren.infrastructure.web.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

import static org.mockito.Mockito.*;

public class KeycloakTokenMock {
    public static void setupTokenMock(Account account) {
        String userName = account.getName();
        String userEmail = account.getEmail();
        Set<String> roles = account.getRoles();
        KeycloakPrincipal principal = mock(KeycloakPrincipal.class, RETURNS_DEEP_STUBS);
        when(principal.getName()).thenReturn(userName);
        when(principal.getKeycloakSecurityContext().getIdToken().getEmail()).thenReturn(userEmail);
        SimpleKeycloakAccount keyaccount = new SimpleKeycloakAccount(principal, roles,
                mock(RefreshableKeycloakSecurityContext.class));
        KeycloakAuthenticationToken authenticationToken = new KeycloakAuthenticationToken(keyaccount, true);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);
    }
}
