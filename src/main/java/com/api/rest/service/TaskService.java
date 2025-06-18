package com.api.rest.service;

import com.api.rest.dto.taskDto.NovaTaskDTO;
import com.api.rest.dto.taskDto.TaskResponseDTO;
import com.api.rest.model.KanbanBoard;
import com.api.rest.model.Task;
import com.api.rest.model.Usuario;
import com.api.rest.repository.KanbanBoardRepository;
import com.api.rest.repository.TaskRepository;
import com.api.rest.repository.UserRepository;
import org.springframework.stereotype.Service;

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

        KanbanBoard board = kanbanBoardRepository.findById(dto.kanbanBoardId())
                .orElseThrow(() -> new RuntimeException("Kanban não encontrado"));

        Task task = new Task();
        task.setTitulo(dto.titulo());
        task.setDescricao(dto.descricao());
        task.setPrioridade(dto.prioridade());
        task.setStatus(dto.status());
        task.setAutor(autor);
        task.setKanbanBoard(board);

        board.getTasks().add(task);

        Task taskSalva = taskRepository.save(task);

        return TaskResponseDTO.fromEntity(taskSalva);
    }
}
