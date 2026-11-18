package com.smartport.auth.presentation.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

import com.smartport.auth.presentation.requests.EchoRequest;
import com.smartport.auth.presentation.responses.EchoResponse;


import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public Map<String, String> checkHealth() {

        return Map.of(
            "status", "UP",
            "service", "auth-service",
            "message", "Service is running successfully"
        );
    }
@PostMapping("/echo")
public EchoResponse echo(@RequestBody EchoRequest request) {

    return new EchoResponse(
        true,
        request.getMessage()
    );
}
}