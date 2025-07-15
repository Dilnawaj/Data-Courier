package com.datacourier.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
@GetMapping("/")
    public ResponseEntity<String>  healthCheckController()
    {
        return ResponseEntity.ok("");
    }

    @GetMapping("/data")
    public ResponseEntity<String>  testApi()
    {
        return ResponseEntity.ok("Test APi");
    }





    @GetMapping("/home")
    public ResponseEntity<String>  testApiHome()
    {
        return ResponseEntity.ok("Test Home");
    }






}
