package com.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

public class PaginacaoDTO<T> {
    @JsonProperty("conteudo")
    private List<T> conteudo;

    @JsonProperty("totalElementos")
    private long totalElementos;

    @JsonProperty("paginaAtual")
    private int paginaAtual;

    @JsonProperty("totalPaginas")
    private int totalPaginas;

    @JsonProperty("ultimaPagina")
    private boolean last;

    @JsonProperty("primeiraPagina")
    private boolean first;

    @JsonProperty("tamanhoPagina")
    private int size;

    @JsonProperty("elementosNaPagina")
    private int elementosNaPagina;

    public PaginacaoDTO(Page<T> pagina) {
        this.conteudo = pagina.getContent();
        this.totalElementos = pagina.getTotalElements();
        this.paginaAtual = pagina.getNumber();
        this.totalPaginas = pagina.getTotalPages();
        this.first = pagina.isFirst();
        this.last = pagina.isLast();
        this.elementosNaPagina = pagina.getNumberOfElements();
        this.size = pagina.getSize();
    }

    public List<T> getConteudo() {
        return conteudo;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isFirst() {
        return first;
    }

    public int getSize() {
        return size;
    }

    public int getElementosNaPagina() {
        return elementosNaPagina;
    }
}

