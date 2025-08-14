package com.api.rest.service;



import com.api.rest.dto.kanbanboardDto.TaskResumoResponseKanbanDTO;
import com.api.rest.model.Cliente;
import com.api.rest.model.KanbanBoard;
import com.api.rest.repository.ClienteRepository;
import com.api.rest.repository.KanbanBoardRepository;
import com.api.rest.repository.TaskRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanbanBoardService {

    private final ClienteRepository clienteRepository;
    private final KanbanBoardRepository kanbanBoardRepository;
    private final TaskRepository taskRepository;

    public KanbanBoardService(ClienteRepository clienteRepository,
                              KanbanBoardRepository kanbanBoardRepository,
                              TaskRepository taskRepository) {
        this.clienteRepository = clienteRepository;
        this.kanbanBoardRepository = kanbanBoardRepository;
        this.taskRepository = taskRepository;
    }

    public List<TaskResumoResponseKanbanDTO> listarTasksDoUsuario(Authentication authentication) {
        Long usuarioId = getUsuarioId(authentication); // pega do JWT
        Cliente cliente = clienteRepository.findByUsuariosUserId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        KanbanBoard kanban = kanbanBoardRepository.findByCliente(cliente)
                .orElseThrow(() -> new RuntimeException("KanbanBoard não encontrado"));

        return taskRepository.findByKanbanBoard(kanban).stream()
                .map(task -> new TaskResumoResponseKanbanDTO(
                        task.getId(),
                        task.getTitulo(),
                        task.getDescricao(),
                        task.getStatus().name(),
                        task.getPrioridade().name(),
                        task.getKanbanBoard().getId(),
                        task.getAutor().getUserId(),
                        task.getAutor().getNome()
                ))
                .toList();
    }

    private Long getUsuarioId(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Long.valueOf(jwt.getSubject());
    }
}