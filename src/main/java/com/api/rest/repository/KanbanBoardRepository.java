package com.api.rest.repository;

import com.api.rest.model.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KanbanBoardRepository extends JpaRepository<KanbanBoard, Long> {
    Optional<KanbanBoard> findByClienteId(Long clienteId);
}