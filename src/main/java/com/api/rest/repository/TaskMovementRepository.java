package com.api.rest.repository;

import com.api.rest.model.TaskMovement;
import com.api.rest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMovementRepository extends JpaRepository<TaskMovement, Long> {
    boolean existsByMovimentadoPor(Usuario usuario);
}

