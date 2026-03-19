package com.life.tracker.controller;


import com.life.tracker.model.ZielEntity;
import com.life.tracker.service.UserService;
import com.life.tracker.service.ZielService;
import jakarta.validation.Valid;
import org.openapitools.api.ApiApi;

import org.openapitools.model.Login;
import org.openapitools.model.Registration;
import org.openapitools.model.Ziel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PersonApiImpl implements ApiApi {

    private final UserService userService;
    private final ZielService zielService;

    public PersonApiImpl(UserService userService, ZielService zielService) {
        this.userService = userService;
        this.zielService = zielService;
    }


    @Override
    public ResponseEntity<Ziel> addGoal(Ziel ziel) {
        return zielService.saveGoal( ziel);
    }

    @Override
    public ResponseEntity<List<Ziel>> getGoalsByEmail(String email) {
        return zielService.getGoal( email);
    }

    @Override
    public ResponseEntity<Map<String, String>> loginUser(Login login) {
        return userService.loginUser(login);
    }


    @Override
    public ResponseEntity<Void> saveRegistration(@Valid @RequestBody Registration registration) {
        return userService.createUser(registration);
    }
}

