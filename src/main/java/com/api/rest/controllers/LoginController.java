package com.api.rest.controllers;

import com.api.rest.dto.LoginRequestDTO;
import com.api.rest.dto.LoginResponseDTO;
import com.api.rest.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = loginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("Logout realizado com sucesso");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody String refreshToken) {
        String newAccessToken = loginService.refreshToken(refreshToken);
        return ResponseEntity.ok(newAccessToken);
    }
}