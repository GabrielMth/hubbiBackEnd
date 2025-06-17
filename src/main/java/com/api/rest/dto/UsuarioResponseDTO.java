package com.api.rest.dto;

public record UsuarioResponseDTO (
        Long id,
        String username,
        String email,
        String nome,
        String role,
        String ultimoLogin,
        String senhaGerada
) {}
