package com.api.rest.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_movements")
public class TaskMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_origem")
    private TaskStatus statusOrigem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_destino")
    private TaskStatus statusDestino;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario movimentadoPor;

    @Column(name = "data_movimentacao")
    private LocalDateTime dataMovimentacao;


    @PrePersist
    protected void onCreate() {
        this.dataMovimentacao = LocalDateTime.now();
    }

    public TaskMovement() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskStatus getStatusOrigem() {
        return statusOrigem;
    }

    public void setStatusOrigem(TaskStatus statusOrigem) {
        this.statusOrigem = statusOrigem;
    }

    public TaskStatus getStatusDestino() {
        return statusDestino;
    }

    public void setStatusDestino(TaskStatus statusDestino) {
        this.statusDestino = statusDestino;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public Usuario getMovimentadoPor() {
        return movimentadoPor;
    }

    public void setMovimentadoPor(Usuario movimentadoPor) {
        this.movimentadoPor = movimentadoPor;
    }
}
