package com.api.rest.dto.usuarioDto;

import java.time.Instant;

public record UsuarioListEquipeDTO (
    Long id,
    String username,
    String email,
    String nome,
    String role,
    boolean ativo,
    Instant ultimoLogin
) {}

