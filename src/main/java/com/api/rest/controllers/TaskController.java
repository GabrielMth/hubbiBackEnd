package com.api.rest.controllers;

import com.api.rest.dto.PaginacaoDTO;
import com.api.rest.dto.taskDto.NovaTaskDTO;
import com.api.rest.dto.taskDto.TaskDetalhamentoDTO;
import com.api.rest.dto.taskDto.TaskResponseDTO;
import com.api.rest.dto.taskDto.TaskTableResponseDTO;
import com.api.rest.model.Cliente;
import com.api.rest.model.KanbanBoard;
import com.api.rest.model.Task;
import com.api.rest.repository.ClienteRepository;
import com.api.rest.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filtro")
    public ResponseEntity<PaginacaoDTO<TaskTableResponseDTO>> filtrarTasks(
            @RequestParam(required = true) Long clienteId,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String prioridade,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim,
            Pageable pageable
            ) {

        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data de início não pode ser posterior à data final."
            );
        }

        Instant inicioInstant = null;
        Instant fimInstant = null;

        if (dataInicio != null) {
            inicioInstant = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toInstant();
        }

        if (dataFim != null) {
            fimInstant = dataFim.atTime(LocalTime.MAX).atZone(ZoneId.of("America/Sao_Paulo")).toInstant();
        }

        var resultado = taskService.listarTasksComFiltro(clienteId, titulo, status, prioridade, inicioInstant, fimInstant, pageable);

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarTask(@PathVariable Long id) {
        taskService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/detalhes/{id}")
    public ResponseEntity<TaskDetalhamentoDTO> detalharTask(@PathVariable Long id) {
        TaskDetalhamentoDTO dto = taskService.buscarDetalhadoPorId(id);
        return ResponseEntity.ok(dto);
    }


}
