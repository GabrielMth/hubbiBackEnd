package com.api.rest.dto.taskDto;

import com.api.rest.model.TaskPrioridade;
import com.api.rest.model.TaskStatus;

import java.time.Instant;

public class TaskTableResponseDTO {
    private Long id;
    private String titulo;
    private TaskPrioridade prioridade;
    private TaskStatus status;
    private KanbanBoardIdDTO kanbanBoard;
    private AutorResumoDTO autor;
    private Instant dataCriacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public TaskPrioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(TaskPrioridade prioridade) {
        this.prioridade = prioridade;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Instant dataCriacao) { this.dataCriacao = dataCriacao; }

    public KanbanBoardIdDTO getKanbanBoard() { return kanbanBoard; }
    public void setKanbanBoard(KanbanBoardIdDTO kanbanBoard) { this.kanbanBoard = kanbanBoard; }

    public AutorResumoDTO getAutor() { return autor; }
    public void setAutor(AutorResumoDTO autor) { this.autor = autor; }

    public static class KanbanBoardIdDTO {
        private Long id;

        public KanbanBoardIdDTO(Long id) { this.id = id; }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }

    public static class AutorResumoDTO {
        private Long userId;
        private String nome;

        public AutorResumoDTO(Long userId, String nome) {
            this.userId = userId;
            this.nome = nome;
        }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

}
