package com.api.rest.controllers;

import com.api.rest.dto.loginDto.ConfirmarTrocaSenhaRequestDTO;
import com.api.rest.dto.loginDto.LoginRequestDTO;
import com.api.rest.dto.loginDto.LoginResponseDTO;
import com.api.rest.dto.loginDto.RecuperarSenhaRequestDTO;
import com.api.rest.service.LoginService;
import com.api.rest.service.RecuperacaoSenhaService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final RecuperacaoSenhaService recuperacaoSenhaService;

    public LoginController(LoginService loginService, RecuperacaoSenhaService recuperacaoSenhaService) {
        this.loginService = loginService;
        this.recuperacaoSenhaService = recuperacaoSenhaService;
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

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody @Valid RecuperarSenhaRequestDTO request) throws MessagingException {
        recuperacaoSenhaService.enviarSenhaTemporaria(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Senha temporária enviada para o email"));
    }

    @PostMapping("/confirmartrocasenha")
    public ResponseEntity<?> confirmarTrocaSenha(@RequestBody @Valid ConfirmarTrocaSenhaRequestDTO request) {
        recuperacaoSenhaService.confirmarTrocaSenha(request.getEmail(), request.getSenhaTemporaria(), request.getNovaSenha());
        return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso!"));
    }

}