package com.api.rest.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private Instant criadoEm;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioMidia> midias = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Comentario(){}

    public Comentario(Long id, String texto, Instant criadoEm, Usuario autor, List<ComentarioMidia> midias, Task task) {
        this.id = id;
        this.texto = texto;
        this.criadoEm = criadoEm;
        this.autor = autor;
        this.midias = midias;
        this.task = task;
    }

    public List<ComentarioMidia> getMidias() {
        return midias;
    }

    public void setMidias(List<ComentarioMidia> midias) {
        this.midias = midias;
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

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}