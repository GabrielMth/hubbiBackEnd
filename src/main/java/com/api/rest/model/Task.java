package com.api.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=5, max = 80)
    private String titulo;

    @NotNull
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPrioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "kanban_board_id")
    private KanbanBoard kanbanBoard;

    @ManyToOne
    @JoinColumn (name = "autor_id")
    private Usuario autor;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskMovement> movimentacoes;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant data_Criacao;

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

    public Instant getData_Criacao() {
        return data_Criacao;
    }

    public void setData_Criacao(Instant data_Criacao) {
        this.data_Criacao = data_Criacao;
    }

    public Task() {

    }

    public Task(Long id, String titulo, String descricao, TaskPrioridade prioridade, TaskStatus status, KanbanBoard kanbanBoard, Usuario autor, List<TaskMovement> movimentacoes, Instant dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.kanbanBoard = kanbanBoard;
        this.autor = autor;
        this.movimentacoes = movimentacoes;
        this.data_Criacao = data_Criacao;
    }
}
