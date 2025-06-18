package com.api.rest.controllers;

import com.api.rest.dto.taskDto.NovaTaskDTO;
import com.api.rest.dto.taskDto.TaskResponseDTO;
import com.api.rest.model.Task;
import com.api.rest.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> criarTask(@RequestBody @Valid NovaTaskDTO dto) {
        TaskResponseDTO novaTaskdto = taskService.criarTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTaskdto);

    }
}
