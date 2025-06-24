package com.api.rest.controllers;

import com.api.rest.dto.PaginacaoDTO;
import com.api.rest.dto.taskDto.NovaTaskDTO;
import com.api.rest.dto.taskDto.TaskResponseDTO;
import com.api.rest.dto.taskDto.TaskTableResponseDTO;
import com.api.rest.model.Cliente;
import com.api.rest.model.KanbanBoard;
import com.api.rest.model.Task;
import com.api.rest.repository.ClienteRepository;
import com.api.rest.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ClienteRepository clienteRepository;

    public TaskController(TaskService taskService, ClienteRepository clienteRepository) {
        this.taskService = taskService;
        this.clienteRepository = clienteRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/criar")
    public ResponseEntity<TaskResponseDTO> criarTask(@RequestBody @Valid NovaTaskDTO dto) {
        TaskResponseDTO novaTaskdto = taskService.criarTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTaskdto);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{clienteId}")
    public ResponseEntity<PaginacaoDTO<TaskTableResponseDTO>> listarTasksPorCliente(
            @PathVariable Long clienteId,
            Pageable pageable) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        KanbanBoard kanbanBoard = cliente.getKanbanBoard();

        PaginacaoDTO<TaskTableResponseDTO> resultado = taskService.listarTasksPorKanbanBoard(kanbanBoard.getId(), pageable);

        return ResponseEntity.ok(resultado);
    }

}
