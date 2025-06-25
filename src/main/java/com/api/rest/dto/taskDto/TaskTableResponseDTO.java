package com.api.rest.dto.taskDto;

import com.api.rest.model.TaskPrioridade;
import com.api.rest.model.TaskStatus;

import java.time.Instant;

public class TaskTableResponseDTO {

    private Long id;
    private String titulo;
    private TaskPrioridade prioridade;
    private TaskStatus status;
    private Instant dataCriacao;
    private AutorResumoDTO autor;
    private KanbanBoardIdDTO kanbanBoard;

    public TaskTableResponseDTO(Long id, String titulo, TaskPrioridade prioridade, TaskStatus status,
                                Instant dataCriacao, AutorResumoDTO autor, KanbanBoardIdDTO kanbanBoard) {
        this.id = id;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.autor = autor;
        this.kanbanBoard = kanbanBoard;
    }

    public TaskTableResponseDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KanbanBoardIdDTO getKanbanBoard() {
        return kanbanBoard;
    }

    public void setKanbanBoard(KanbanBoardIdDTO kanbanBoard) {
        this.kanbanBoard = kanbanBoard;
    }

    public AutorResumoDTO getAutor() {
        return autor;
    }

    public void setAutor(AutorResumoDTO autor) {
        this.autor = autor;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPrioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(TaskPrioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
