package mops.foren.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WORKAROUND for https://issues.redhat.com/browse/KEYCLOAK-11282.
 * Bean should move into {@link SecurityConfig} once Bug has been resolved.
 */

@Configuration
class KeycloakConfig {
    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
