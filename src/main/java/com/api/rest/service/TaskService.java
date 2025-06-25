package com.api.rest.service;

import com.api.rest.dto.PaginacaoDTO;
import com.api.rest.dto.taskDto.*;
import com.api.rest.model.*;
import com.api.rest.repository.KanbanBoardRepository;
import com.api.rest.repository.TaskRepository;
import com.api.rest.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final KanbanBoardRepository kanbanBoardRepository;
    private final TaskRepository taskRepository;

    public TaskService (UserRepository userRepository, KanbanBoardRepository kanbanBoardRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.kanbanBoardRepository = kanbanBoardRepository;
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO criarTask(NovaTaskDTO dto) {
        Usuario autor = userRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        KanbanBoard board = kanbanBoardRepository.findByClienteId(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Kanban não encontrado"));

        Task task = new Task();
        task.setTitulo(dto.titulo());
        task.setDescricao(dto.descricao());
        task.setPrioridade(dto.prioridade());
        task.setStatus(dto.status());
        task.setAutor(autor);
        task.setKanbanBoard(board);
        task.setData_Criacao(Instant.now());

        board.getTasks().add(task);

        Task taskSalva = taskRepository.save(task);

        return TaskResponseDTO.fromEntity(taskSalva);
    }


    public PaginacaoDTO<TaskTableResponseDTO> listarTasksPorKanbanBoard(Long kanbanBoardId, Pageable pageable) {
        Page<Task> pageResult = taskRepository.findByKanbanBoardId(kanbanBoardId, pageable);
        Page<TaskTableResponseDTO> dtoPage = pageResult.map(this::mapTableToDTO);
        return new PaginacaoDTO<>(dtoPage);
    }

    public PaginacaoDTO<TaskTableResponseDTO> listarTasksComFiltro(
            Long clienteId,
            String titulo,
            String status,
            String prioridade,
            Instant dataInicio,
            Instant dataFim,
            Pageable pageable) {

        TaskStatus statusEnum = null;
        if (status != null && !status.isBlank()) {
            try {
                statusEnum = TaskStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + status);
            }
        }

        TaskPrioridade prioridadeEnum = null;
        if (prioridade != null && !prioridade.isBlank()) {
            try {
                prioridadeEnum = TaskPrioridade.valueOf(prioridade.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Prioridade inválida: " + prioridade);
            }
        }

        Page<TaskTableResponseDTO> result = taskRepository.listarTasksComFiltro(
                clienteId, titulo, statusEnum, prioridadeEnum, dataInicio, dataFim, pageable);

        return new PaginacaoDTO<>(result);
    }

    private TaskTableResponseDTO mapTableToDTO(Task task) {
        TaskTableResponseDTO dto = new TaskTableResponseDTO();
        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setStatus(task.getStatus());
        dto.setPrioridade(task.getPrioridade());
        dto.setDataCriacao(task.getData_Criacao());


        if (task.getAutor() != null) {
            AutorResumoDTO autorDTO = new AutorResumoDTO(
                    task.getAutor().getUserId(),
                    task.getAutor().getNome()
            );
            dto.setAutor(autorDTO);
        } else {
            dto.setAutor(null);
        }

        if (task.getKanbanBoard() != null) {
            KanbanBoardIdDTO kanbanDTO =
                    new KanbanBoardIdDTO(task.getKanbanBoard().getId());
            dto.setKanbanBoard(kanbanDTO);
        } else {
            dto.setKanbanBoard(null);
        }

        return dto;
    }


}
