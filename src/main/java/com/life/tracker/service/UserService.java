package com.life.tracker.service;


import com.life.tracker.model.UserEntity;
import com.life.tracker.repo.UserRepository;
import org.openapitools.model.Login;
import org.openapitools.model.Registration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Map;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakClient keycloak;

    public UserService(UserRepository userRepository, KeycloakClient keycloak) {
        this.userRepository = userRepository;
        this.keycloak = keycloak;
    }

    public ResponseEntity<Void> createUser(Registration registration) {
        String token = keycloak.getAdminToken();

        boolean exists = keycloak.getAllUsers(token).stream()
                .anyMatch(u -> registration.getEmail().equals(u.getEmail()));

        if (exists) {
            saveUserInDB(registration);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String userId = keycloak.createUser(registration, token);

        keycloak.setPassword(userId, token, registration.getPasswort());
        keycloak.assignRole(userId, token, "ZielCreator");

        saveUserInDB(registration);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Map<String, String>> loginUser(Login login) {
        try {
            String token = keycloak.login(login.getEmail(), login.getPasswort());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Keycloak Fehler: " + e.getResponseBodyAsString()));
        }
    }
    private void saveUserInDB(Registration registration) {
        userRepository.findByEmail(registration.getEmail())
                .orElseGet(() -> {
                    UserEntity user = new UserEntity();
                    user.setVorname(registration.getVorname());
                    user.setNachname(registration.getNachname());
                    user.setGeburtsdatum(registration.getGeburtsdatum());
                    user.setLand(registration.getLand());
                    user.setEmail(registration.getEmail());
                    return userRepository.save(user);
                });
    }
}




