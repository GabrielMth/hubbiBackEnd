package com.api.rest.dto.usuarioDto;

public record UsuarioDTO(
        Long id,
        String username,
        String password,
        String email,
        String nome,
        String role,
        boolean ativo,
        String ultimoLogin
) {}

