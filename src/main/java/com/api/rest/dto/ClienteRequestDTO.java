package com.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ClienteRequestDTO(
        @NotBlank String nome,
        @NotBlank String documento,
        @NotBlank String celular,
        String telefone,
        EnderecoDTO endereco
) {}