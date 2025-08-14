package com.api.rest.repository;

import com.api.rest.model.Cliente;
import com.api.rest.model.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KanbanBoardRepository extends JpaRepository<KanbanBoard, Long> {
    Optional<KanbanBoard> findByClienteId(Long clienteId);

    Optional<KanbanBoard> findByCliente(Cliente cliente);






}