package com.api.rest.repository;

import com.api.rest.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByClienteId(Long clienteId);

    Page<Usuario> findByClienteId(Long clienteId, Pageable pageable);

    Optional<Usuario> findByUserId(Long userId);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Usuario> findByEmail(String email);

}
