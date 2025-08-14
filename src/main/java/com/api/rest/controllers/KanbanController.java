package com.api.rest.controllers;


import com.api.rest.dto.kanbanboardDto.TaskResumoResponseKanbanDTO;
import com.api.rest.service.KanbanBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/kanban")
public class KanbanController {

    private final KanbanBoardService kanbanBoardService;

    public KanbanController(KanbanBoardService kanbanBoardService) {
        this.kanbanBoardService = kanbanBoardService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResumoResponseKanbanDTO>> listarTasks(Authentication authentication) {
        List<TaskResumoResponseKanbanDTO> tasks = kanbanBoardService.listarTasksDoUsuario(authentication);
        return ResponseEntity.ok(tasks);
    }

}
