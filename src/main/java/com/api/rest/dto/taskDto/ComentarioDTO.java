package com.api.rest.dto.taskDto;

import com.api.rest.model.Comentario;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class ComentarioDTO {
    private Long id;
    private String texto;
    private Instant criadoEm;
    private String autor;
    private List<ComentarioMidiaDTO> midias;

    public static ComentarioDTO fromEntity(Comentario c) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.id = c.getId();
        dto.texto = c.getTexto();
        dto.criadoEm = c.getCriadoEm();
        dto.autor = c.getAutor() != null ? c.getAutor().getNome() : null;

        if (c.getMidias() != null) {
            dto.midias = c.getMidias().stream()
                    .map(ComentarioMidiaDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        return dto;
    }

    public ComentarioDTO() {
    }

    public ComentarioDTO(Long id, String autor, List<ComentarioMidiaDTO> midias, Instant criadoEm, String texto) {
        this.id = id;
        this.autor = autor;
        this.midias = midias;
        this.criadoEm = criadoEm;
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ComentarioMidiaDTO> getMidias() {
        return midias;
    }

    public void setMidias(List<ComentarioMidiaDTO> midias) {
        this.midias = midias;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
