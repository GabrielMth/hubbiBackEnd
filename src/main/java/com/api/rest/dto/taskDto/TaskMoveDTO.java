package com.api.rest.dto.taskDto;

import com.api.rest.model.TaskStatus;

public class TaskMoveDTO {
    private Long taskId;
    private TaskStatus statusOrigem;  // opcional, pode vir do frontend
    private TaskStatus statusDestino;

    public TaskMoveDTO() {

    }

    public TaskMoveDTO(Long taskId, TaskStatus statusOrigem, TaskStatus statusDestino) {
        this.taskId = taskId;
        this.statusOrigem = statusOrigem;
        this.statusDestino = statusDestino;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setStatusOrigem(TaskStatus statusOrigem) {
        this.statusOrigem = statusOrigem;
    }

    public void setStatusDestino(TaskStatus statusDestino) {
        this.statusDestino = statusDestino;
    }

    public TaskStatus getStatusOrigem() {
        return statusOrigem;
    }

    public TaskStatus getStatusDestino() {
        return statusDestino;
    }
}