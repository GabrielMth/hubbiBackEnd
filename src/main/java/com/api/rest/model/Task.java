package com.api.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Set;

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
    private Instant dataCriacao;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> comentarios;

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

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
        return dataCriacao;
    }

    public void setData_Criacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Task() {

    }

    public Task(Long id, Set<Comentario> comentarios, Instant dataCriacao, List<TaskMovement> movimentacoes, Usuario autor, TaskStatus status, KanbanBoard kanbanBoard, TaskPrioridade prioridade, String descricao, String titulo) {
        this.id = id;
        this.comentarios = comentarios;
        this.dataCriacao = dataCriacao;
        this.movimentacoes = movimentacoes;
        this.autor = autor;
        this.status = status;
        this.kanbanBoard = kanbanBoard;
        this.prioridade = prioridade;
        this.descricao = descricao;
        this.titulo = titulo;
    }
}
