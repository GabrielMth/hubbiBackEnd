package com.api.rest.dto;

public record UsuarioDTO(
        Long id,
        String username,
        String password,
        String email,
        String nome,
        String role,
        String ultimoLogin
) {}

