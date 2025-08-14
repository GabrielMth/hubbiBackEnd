package com.api.rest.dto.loginDto;

import jakarta.validation.constraints.NotBlank;

public class ConfirmarTrocaSenhaRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String senhaTemporaria;
    @NotBlank
    private String novaSenha;

    // getters e setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaTemporaria() { return senhaTemporaria; }
    public void setSenhaTemporaria(String senhaTemporaria) { this.senhaTemporaria = senhaTemporaria; }

    public String getNovaSenha() { return novaSenha; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
}
