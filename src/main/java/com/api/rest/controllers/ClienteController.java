package com.api.rest.controllers;


import com.api.rest.dto.ClienteRequestDTO;
import com.api.rest.dto.PaginacaoDTO;
import com.api.rest.event.RecursoCriadoEvent;
import com.api.rest.model.Cliente;
import com.api.rest.repository.ClienteRepository;
import com.api.rest.service.ClienteService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
@PreAuthorize("hasRole('ADMIN')")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ClienteService clienteService;


    @GetMapping
    public PaginacaoDTO<Cliente> listaCmFiltroClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findAll(pageable);

        return new PaginacaoDTO<>(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterClienteId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }


    @GetMapping(params = "nome")
    public PaginacaoDTO<Cliente> filtrarClientesNome(@RequestParam("nome") String nome, Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.filtrarPorNome(nome, pageable);

        return new PaginacaoDTO<>(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody ClienteRequestDTO dto, HttpServletResponse response) {
        Cliente clienteSalvo = clienteService.criarCliente(dto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EmptyResultDataAccessException(1); // Lança exceção sem mensagem
        }
        clienteRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.atualizar(id, cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
        clienteService.atualizarPropriedadeAtivo(id,ativo);
    }






}