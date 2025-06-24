package com.api.rest.dto.taskDto;

import com.api.rest.model.TaskPrioridade;
import com.api.rest.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NovaTaskDTO(
        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotNull TaskPrioridade prioridade,
        @NotNull TaskStatus status,
        @NotNull Long clienteId,
        @NotNull Long autorId
) {}