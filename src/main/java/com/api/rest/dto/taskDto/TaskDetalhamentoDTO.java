package com.api.rest.dto.taskDto;

import com.api.rest.model.Comentario;
import com.api.rest.model.Task;
import com.api.rest.model.TaskMovement;
import com.api.rest.model.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDetalhamentoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private String prioridade;
    private Instant dataCriacao;

    private AutorResumoDTO autor;

    private List<ComentarioDTO> comentarios;
    private List<MovimentacaoDTO> movimentacoes;

    public static TaskDetalhamentoDTO fromEntity(Task task) {
        TaskDetalhamentoDTO dto = new TaskDetalhamentoDTO();

        dto.id = task.getId();
        dto.titulo = task.getTitulo();
        dto.descricao = task.getDescricao();
        dto.status = task.getStatus().name();
        dto.prioridade = task.getPrioridade().name();
        dto.dataCriacao = task.getData_Criacao();

        Usuario autor = task.getAutor();
        dto.autor = new AutorResumoDTO(autor.getUserId(), autor.getNome());

        dto.comentarios = task.getComentarios() != null
                ? task.getComentarios().stream().map(ComentarioDTO::fromEntity).collect(Collectors.toList())
                : List.of();

        dto.movimentacoes = task.getMovimentacoes() != null
                ? task.getMovimentacoes().stream().map(MovimentacaoDTO::fromEntity).collect(Collectors.toList())
                : List.of();

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MovimentacaoDTO> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoDTO> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public AutorResumoDTO getAutor() {
        return autor;
    }

    public void setAutor(AutorResumoDTO autor) {
        this.autor = autor;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public static class AutorResumoDTO {
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

    public static class ComentarioDTO {
        private String texto;
        private String midiaUrl;
        private Instant criadoEm;
        private String autor;

        public static ComentarioDTO fromEntity(Comentario c) {
            ComentarioDTO dto = new ComentarioDTO();
            dto.texto = c.getTexto();
            dto.midiaUrl = c.getMidiaUrl();
            dto.criadoEm = c.getCriadoEm();
            dto.autor = c.getAutor() != null ? c.getAutor().getNome() : null;
            return dto;
        }

        public String getTexto() {
            return texto;
        }

        public void setTexto(String texto) {
            this.texto = texto;
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

        public String getMidiaUrl() {
            return midiaUrl;
        }

        public void setMidiaUrl(String midiaUrl) {
            this.midiaUrl = midiaUrl;
        }
    }

    public static class MovimentacaoDTO {
        private String statusOrigem;
        private String statusDestino;
        private String movimentadoPor;
        private LocalDateTime dataMovimentacao;

        public static MovimentacaoDTO fromEntity(TaskMovement m) {
            MovimentacaoDTO dto = new MovimentacaoDTO();
            dto.statusOrigem = m.getStatusOrigem().name();
            dto.statusDestino = m.getStatusDestino().name();
            dto.movimentadoPor = m.getMovimentadoPor() != null ? m.getMovimentadoPor().getNome() : null;
            dto.dataMovimentacao = m.getDataMovimentacao();
            return dto;
        }

        public String getStatusOrigem() {
            return statusOrigem;
        }

        public void setStatusOrigem(String statusOrigem) {
            this.statusOrigem = statusOrigem;
        }

        public LocalDateTime getDataMovimentacao() {
            return dataMovimentacao;
        }

        public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
            this.dataMovimentacao = dataMovimentacao;
        }

        public String getStatusDestino() {
            return statusDestino;
        }

        public void setStatusDestino(String statusDestino) {
            this.statusDestino = statusDestino;
        }

        public String getMovimentadoPor() {
            return movimentadoPor;
        }

        public void setMovimentadoPor(String movimentadoPor) {
            this.movimentadoPor = movimentadoPor;
        }
    }
}
