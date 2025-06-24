package com.api.rest.repository;

import com.api.rest.dto.clienteDto.ClienteDropdownDTO;
import com.api.rest.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(
            "SELECT c FROM Cliente c " +
                    "WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))"
    )
    Page<Cliente> filtrarPorNome(String nome, Pageable pageable);

    @Query("SELECT new com.api.rest.dto.clienteDto.ClienteDropdownDTO(c.id, c.nome, c.documento, c.kanbanBoard.id) FROM Cliente c")
    List<ClienteDropdownDTO> buscarParaDropDown();

}