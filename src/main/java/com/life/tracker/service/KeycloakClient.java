package com.life.tracker.service;


import org.keycloak.representations.account.UserRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

import org.mapstruct.Qualifier;
import org.openapitools.model.Registration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;
import java.util.List;

@Service
public class KeycloakClient {

    private final WebClient client =WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    public KeycloakClient() {

    }


    private static final String CLIENT_ID = "admin-client";
    private static final String CLIENT_SECRET = "bHghedsAFLvJOd15ZT803Y9MQQ0zzEZ8";

    public String getAdminToken() {
        return client.post()
                .uri("/realms/life-tracker/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", "admin-client")
                        .with("client_secret", "bHghedsAFLvJOd15ZT803Y9MQQ0zzEZ8")
                        .with("grant_type", "client_credentials"))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("access_token").asText())
                .block();
    }

    public List<UserRepresentation> getAllUsers(String token) {
        return client.get()
                .uri("/admin/realms/life-tracker/users")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(UserRepresentation.class)
                .collectList()
                .block();
    }

    public String createUser(Registration registration, String token) {
        ObjectNode user = JsonNodeFactory.instance.objectNode();
        user.put("username", registration.getEmail());
        user.put("email", registration.getEmail());
        user.put("firstName", registration.getVorname());
        user.put("lastName", registration.getNachname());
        user.put("enabled", true);

        return client.post()
                .uri("/admin/realms/life-tracker/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchangeToMono(response -> {
                    String location = response.headers().asHttpHeaders().getFirst("Location");
                    return Mono.just(location.substring(location.lastIndexOf("/") + 1));
                })
                .block();
    }

    public void setPassword(String userId, String token, String password) {
        ObjectNode payload = JsonNodeFactory.instance.objectNode();
        payload.put("type", "password");
        payload.put("value", password);
        payload.put("temporary", false);

        client.put()
                .uri("/admin/realms/life-tracker/users/" + userId + "/reset-password")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void assignRole(String userId, String token, String roleName) {
        RoleRepresentation role = client.get()
                .uri("/admin/realms/life-tracker/roles/" + roleName)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(RoleRepresentation.class)
                .block();

        client.post()
                .uri("/admin/realms/life-tracker/users/" + userId + "/role-mappings/realm")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(role))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public String login(String email, String password) {
        JsonNode jsonNode = client.post()
                .uri("/realms/life-tracker/protocol/openid-connect/token")
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("client_id", CLIENT_ID)
                        .with("client_secret", CLIENT_SECRET)
                        .with("username", email)
                        .with("password", password)
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        return jsonNode.get("access_token").asText();
    }
}

