package com.api.rest.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Endereco {

    @NotNull
    private String rua;
    @NotNull
    private String numero;
    @NotNull
    private String bairro;
    @NotNull
    private String cep;
    @NotNull
    private String cidade;
    @NotNull
    private String estado;

    // Construtor padrão
    public Endereco() {
    }

    // Construtor com parâmetros
    public Endereco(String rua, String numero, String bairro, String cep, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Getters e Setters
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
