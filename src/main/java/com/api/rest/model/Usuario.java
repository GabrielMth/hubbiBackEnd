package com.api.rest.model;


import com.api.rest.dto.LoginRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;

@Entity
@Table (name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    private String nome;

    @Column(unique = true)
    @NotNull
    @Size(min=5, max = 50)
    private String username;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    @Size(min=5, max = 150)
    private String password;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(name = "ultimo_login")
    private Instant ultimoLogin;


    public Usuario() {
    }

    public Usuario(Long userId, String nome, String username, String email, String password, Cliente cliente, Set<Role> roles) {
        this.userId = userId;
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.cliente = cliente;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoginCorrect (LoginRequestDTO loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }

    public void atualizarUltimoLogin() {
        this.ultimoLogin = Instant.now();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario u = (Usuario) o;
        return userId != null && userId.equals(u.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Instant getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Instant ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }
}

