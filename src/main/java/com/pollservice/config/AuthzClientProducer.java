package com.pollservice.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;

import java.util.Map;

@ApplicationScoped
public class AuthzClientProducer {

    @ConfigProperty(name = "keycloak.base-server-url")
    String baseServerUrl;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String secret;

    @Produces
    public AuthzClient createAuthzClient() {
        Configuration configuration = new Configuration(
                baseServerUrl,
                "PollsRealm",
                clientId,
                Map.of("secret", (Object)secret),
                null);
        return AuthzClient.create(configuration);
    }
}
