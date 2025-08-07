package com.api.rest.dto.taskDto;

import com.api.rest.model.ComentarioMidia;

public class ComentarioMidiaDTO {
    private String url;
    private String tipo;

    public static ComentarioMidiaDTO fromEntity(ComentarioMidia midia) {
        ComentarioMidiaDTO dto = new ComentarioMidiaDTO();
        dto.url = midia.getUrl();
        dto.tipo = midia.getTipo();
        return dto;
    }

    public ComentarioMidiaDTO() {
    }

    public ComentarioMidiaDTO(String url, String tipo) {
        this.url = url;
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
