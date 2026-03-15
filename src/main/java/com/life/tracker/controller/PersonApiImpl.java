package com.life.tracker.controller;


import com.life.tracker.service.CsvService;
import org.openapitools.api.ApiApi;

import org.openapitools.model.Registration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
public class PersonApiImpl implements ApiApi {
    private final CsvService csvService;

    public PersonApiImpl(CsvService csvService) {
        this.csvService = csvService;
    }


    @Override
    public ResponseEntity<Void> saveRegistration(Registration registration) {
        return csvService.createUser(registration);
    }
}
