package com.api.rest.dto.taskDto;

public class AutorResumoDTO {
    private Long userId;
    private String nome;

    public AutorResumoDTO(Long userId, String nome) {
        this.userId = userId;
        this.nome = nome;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}