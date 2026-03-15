package com.life.tracker.service;




import org.openapitools.model.Registration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Service
public class CsvService {

    private static final String FILE_PATH = "personen.csv";

    public ResponseEntity<Void> saveToCsv(Registration person) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.append(person.getVorname())
                    .append(";")
                    .append(person.getNachname())
                    .append(";")
                    .append(person.getEmail())
                    .append(";")
                    .append(person.getGeburtsdatum())
                    .append(";")
                    .append(person.getLand())
                    .append("\n");
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Schreiben in KEYCLOACK", e);
        }
        return null;
    }

    public ResponseEntity<Void>  createUser(Registration registration) {
        String token = getAdminToken();
        WebClient client = WebClient.builder().build();

        ObjectNode user = JsonNodeFactory.instance.objectNode();
        user.put("username", "test123");
        user.put("email", "test@example.com");
        user.put("firstName", "Max");
        user.put("lastName", "Mustermann");
        user.put("enabled", true);

        client.post()
                .uri("http://localhost:8080/admin/realms/life-tracker/users")
                .header("Authorization","Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        return null;
    }

    public String getAdminToken() {
        String clientSecret = "bHghedsAFLvJOd15ZT803Y9MQQ0zzEZ8";
        return WebClient.create()
                .post()
                .uri("http://localhost:8080/realms/life-tracker/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", "admin-client")
                        .with("client_secret", clientSecret)
                        .with("grant_type", "client_credentials"))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("access_token").asText())
                .block();
    }


}


