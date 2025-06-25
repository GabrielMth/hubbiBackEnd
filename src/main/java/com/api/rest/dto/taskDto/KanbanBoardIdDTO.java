package com.api.rest.dto.taskDto;

public class KanbanBoardIdDTO {
    private Long id;

    public KanbanBoardIdDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}