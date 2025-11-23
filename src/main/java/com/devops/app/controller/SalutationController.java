package com.devops.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SalutationController {

    @GetMapping("/salutation")
    public Map<String, Object> salutation() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bonjour Master DevOps II");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", "success");
        return response;
    }
    
    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("application", "Test DevOps");
        response.put("version", "1.0.0");
        response.put("status", "running");
        return response;
    }
}
