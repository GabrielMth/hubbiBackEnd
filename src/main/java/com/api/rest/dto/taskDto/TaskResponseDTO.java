package com.api.rest.dto.taskDto;

import com.api.rest.model.Task;
import com.api.rest.model.Usuario;

import java.time.Instant;

public class TaskResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String status;
    private KanbanBoardIdDTO kanbanBoard;
    private AutorResumoDTO autor;
    private Instant dataCriacao;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

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

    public static TaskResponseDTO fromEntity(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();

        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setDescricao(task.getDescricao());
        dto.setPrioridade(task.getPrioridade().name());
        dto.setStatus(task.getStatus().name());
        dto.setDataCriacao(task.getData_Criacao());

        dto.setKanbanBoard(new KanbanBoardIdDTO(task.getKanbanBoard().getId()));

        Usuario autor = task.getAutor();
        dto.setAutor(new AutorResumoDTO(autor.getUserId(), autor.getNome()));

        return dto;
    }
}
