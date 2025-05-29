package com.api.rest.dto;

public record EnderecoDTO(
        String rua,
        String numero,
        String bairro,
        String cep,
        String cidade,
        String estado
) {}
