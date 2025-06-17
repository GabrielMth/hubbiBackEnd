package com.api.rest.repository;

import com.api.rest.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Page<Usuario> findByClienteId(Long clienteId, Pageable pageable);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
