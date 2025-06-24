package com.api.rest.dto.clienteDto;

import com.api.rest.dto.EnderecoDTO;

import java.time.LocalDateTime;

public record ClienteDTO(
        Long id,
        String nome,
        String documento,
        String celular,
        Boolean ativo,
        String telefone,
        LocalDateTime dataCadastro,
        EnderecoDTO endereco
) {}