package com.api.rest.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name="comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    private String midiaUrl; // video ou imagem

    private LocalDateTime criadoEm;

    @ManyToOne
    private Usuario autor;

    @ManyToOne
    private Task task;
}