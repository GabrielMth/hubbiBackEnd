package com.api.rest.dto.taskDto;

import java.time.LocalDateTime;

public class TaskMoveResponseDTO {
    private Long taskId;
    private String colunaOrigem;
    private String colunaDestino;
    private String movimentadoPor;
    private LocalDateTime dataMovimentacao;

    public TaskMoveResponseDTO(Long taskId, String colunaOrigem, String colunaDestino, String movimentadoPor, LocalDateTime dataMovimentacao) {
        this.taskId = taskId;
        this.colunaOrigem = colunaOrigem;
        this.colunaDestino = colunaDestino;
        this.movimentadoPor = movimentadoPor;
        this.dataMovimentacao = dataMovimentacao;
    }

    public TaskMoveResponseDTO(){}

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getMovimentadoPor() {
        return movimentadoPor;
    }

    public void setMovimentadoPor(String movimentadoPor) {
        this.movimentadoPor = movimentadoPor;
    }

    public String getColunaDestino() {
        return colunaDestino;
    }

    public void setColunaDestino(String colunaDestino) {
        this.colunaDestino = colunaDestino;
    }

    public String getColunaOrigem() {
        return colunaOrigem;
    }

    public void setColunaOrigem(String colunaOrigem) {
        this.colunaOrigem = colunaOrigem;
    }
}