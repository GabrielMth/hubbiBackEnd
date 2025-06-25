package com.api.rest.repository;


import com.api.rest.dto.taskDto.TaskTableResponseDTO;
import com.api.rest.model.Task;
import com.api.rest.model.TaskPrioridade;
import com.api.rest.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
    SELECT new com.api.rest.dto.taskDto.TaskTableResponseDTO(
        t.id,
        t.titulo,
        t.prioridade,
        t.status,
        t.dataCriacao,
        new com.api.rest.dto.taskDto.AutorResumoDTO(a.userId, a.nome),
        new com.api.rest.dto.taskDto.KanbanBoardIdDTO(k.id)
    )
    FROM Task t
    LEFT JOIN t.autor a
    LEFT JOIN t.kanbanBoard k
    WHERE (:clienteId IS NULL OR k.cliente.id = :clienteId)
      AND (:titulo IS NULL OR LOWER(t.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))
      AND (:status IS NULL OR t.status = :status)
      AND (:prioridade IS NULL OR t.prioridade = :prioridade)
      AND (:dataInicio IS NULL OR t.dataCriacao >= :dataInicio)
      AND (:dataFim IS NULL OR t.dataCriacao <= :dataFim)
""")
    Page<TaskTableResponseDTO> listarTasksComFiltro(
            @Param("clienteId") Long clienteId,
            @Param("titulo") String titulo,
            @Param("status") TaskStatus status,
            @Param("prioridade") TaskPrioridade prioridade,
            @Param("dataInicio") Instant dataInicio,
            @Param("dataFim") Instant dataFim,
            Pageable pageable
    );


    Page<Task> findByKanbanBoardId(Long kanbanBoardId, Pageable pageable);


}
