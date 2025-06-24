package com.api.rest.service;

import com.api.rest.dto.PaginacaoDTO;
import com.api.rest.dto.taskDto.NovaTaskDTO;
import com.api.rest.dto.taskDto.TaskResponseDTO;
import com.api.rest.dto.taskDto.TaskTableResponseDTO;
import com.api.rest.model.Cliente;
import com.api.rest.model.KanbanBoard;
import com.api.rest.model.Task;
import com.api.rest.model.Usuario;
import com.api.rest.repository.KanbanBoardRepository;
import com.api.rest.repository.TaskRepository;
import com.api.rest.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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

    private TaskTableResponseDTO mapTableToDTO(Task task) {
        TaskTableResponseDTO dto = new TaskTableResponseDTO();
        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setStatus(task.getStatus());
        dto.setPrioridade(task.getPrioridade());
        dto.setDataCriacao(task.getData_Criacao());


        // Mapear autor
        if (task.getAutor() != null) {
            // supondo que autor tenha getUserId() e getNome()
            TaskTableResponseDTO.AutorResumoDTO autorDTO = new TaskTableResponseDTO.AutorResumoDTO(
                    task.getAutor().getUserId(),
                    task.getAutor().getNome()
            );
            dto.setAutor(autorDTO);
        } else {
            dto.setAutor(null);
        }

        // Se desejar, mapear o KanbanBoard também
        if (task.getKanbanBoard() != null) {
            TaskTableResponseDTO.KanbanBoardIdDTO kanbanDTO =
                    new TaskTableResponseDTO.KanbanBoardIdDTO(task.getKanbanBoard().getId());
            dto.setKanbanBoard(kanbanDTO);
        } else {
            dto.setKanbanBoard(null);
        }

        return dto;
    }
}
