package com.example.helphub.controller;

import com.example.helphub.dto.UserDTO;
import com.example.helphub.entity.User;
import com.example.helphub.response.AuthenticationResponse;
import com.example.helphub.securityservice.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody User request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
