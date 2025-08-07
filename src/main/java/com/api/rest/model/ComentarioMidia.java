package com.api.rest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comentarios_midias")
public class ComentarioMidia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;   // url da mídia

    private String tipo;  // "image", "video", etc

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;

    public ComentarioMidia() {}

    public ComentarioMidia(String url, String tipo, Comentario comentario) {
        this.url = url;
        this.tipo = tipo;
        this.comentario = comentario;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Comentario getComentario() { return comentario; }
    public void setComentario(Comentario comentario) { this.comentario = comentario; }
}
