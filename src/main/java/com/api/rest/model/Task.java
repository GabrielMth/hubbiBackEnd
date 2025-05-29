package com.api.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TaskStatus prioridade;

    @ManyToOne
    @JoinColumn(name = "kanban_board_id")
    private KanbanBoard kanbanBoard;

    @ManyToOne
    @JoinColumn (name = "autor_id")
    private Usuario autor;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskMovement> movimentacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TaskMovement> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<TaskMovement> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public KanbanBoard getKanbanBoard() {
        return kanbanBoard;
    }

    public void setKanbanBoard(KanbanBoard kanbanBoard) {
        this.kanbanBoard = kanbanBoard;
    }

    public TaskStatus getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(TaskStatus prioridade) {
        this.prioridade = prioridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Task() {

    }

    public Task(Long id, String titulo, String descricao, TaskStatus prioridade, KanbanBoard kanbanBoard, Usuario autor, List<TaskMovement> movimentacoes) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.kanbanBoard = kanbanBoard;
        this.autor = autor;
        this.movimentacoes = movimentacoes;
    }
}
