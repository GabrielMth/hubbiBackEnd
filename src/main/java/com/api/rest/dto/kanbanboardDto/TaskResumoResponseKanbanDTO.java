package com.api.rest.dto.kanbanboardDto;

public record TaskResumoResponseKanbanDTO(
    Long id,
    String titulo,
    String descricao,
    String status,
    String prioridade,
    Long kanbanBoardId,
    Long autorId,
    String autorNome
) {}
