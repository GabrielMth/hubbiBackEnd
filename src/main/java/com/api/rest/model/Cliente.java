package com.api.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Entity
@Table(name="clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=5, max = 60)
    private String nome;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios = new ArrayList<>();

    @NotNull
    private String documento;

    @NotNull
    private String celular;

    private String telefone;

    @Embedded
    private Endereco endereco;

    @NotNull
    private boolean ativo;

    @CreationTimestamp
    private LocalDateTime data_Cadastro;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private KanbanBoard kanbanBoard;


    public Cliente() {

    }

    public Cliente(String nome, String documento, boolean ativo, LocalDateTime data_Cadastro, Endereco endereco, KanbanBoard kanbanBoard) {
        this.nome = nome;
        this.ativo = ativo;
        this.endereco = endereco;
        this.documento = documento;
        this.data_Cadastro = data_Cadastro;
        this.kanbanBoard = kanbanBoard;
    }

    public LocalDateTime getDataCadastro() {
        return data_Cadastro;
    }

    public void setDataCadastro(LocalDateTime data_Cadastro) {
        this.data_Cadastro = data_Cadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public KanbanBoard getKanbanBoard() {
        return kanbanBoard;
    }

    public void setKanbanBoard(KanbanBoard kanbanBoard) {
        this.kanbanBoard = kanbanBoard;
    }

    public LocalDateTime getData_Cadastro() {
        return data_Cadastro;
    }

    public void setData_Cadastro(LocalDateTime data_Cadastro) {
        this.data_Cadastro = data_Cadastro;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @JsonIgnore
    @Transient
    public boolean isInativo() {
        return !this.ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return ativo == cliente.ativo && Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(endereco, cliente.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, ativo, endereco);
    }
}