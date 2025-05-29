package com.api.rest.dto;

public record UsuarioDTO(
        String username,
        String password,
        String email,
        String nome,
        String role
) {}

