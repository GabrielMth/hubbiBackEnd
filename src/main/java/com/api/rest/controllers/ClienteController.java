package com.api.rest.controllers;


import com.api.rest.dto.*;
import com.api.rest.event.RecursoCriadoEvent;
import com.api.rest.model.Cliente;
import com.api.rest.model.Usuario;
import com.api.rest.repository.ClienteRepository;
import com.api.rest.repository.UserRepository;
import com.api.rest.service.ClienteService;
import com.api.rest.service.UsuarioService;
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

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private UsuarioService userService;


    @GetMapping
    public PaginacaoDTO<Cliente> listaCmFiltroClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findAll(pageable);

        return new PaginacaoDTO<>(clientes);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obterClienteId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.map(c -> {

                    EnderecoDTO enderecoDTO = new EnderecoDTO(
                            c.getEndereco().getRua(),
                            c.getEndereco().getNumero(),
                            c.getEndereco().getBairro(),
                            c.getEndereco().getCep(),
                            c.getEndereco().getCidade(),
                            c.getEndereco().getEstado()
                    );
                    ClienteDTO clienteDTO = new ClienteDTO(
                            c.getId(),
                            c.getNome(),
                            c.getDocumento(),
                            c.getCelular(),
                            c.isAtivo(),
                            c.getTelefone(),
                            c.getDataCadastro(),
                            enderecoDTO
                    );
                    return ResponseEntity.ok(clienteDTO);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping(params = "nome")
    public PaginacaoDTO<Cliente> filtrarClientesNome(@RequestParam("nome") String nome, Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.filtrarPorNome(nome, pageable);

        return new PaginacaoDTO<>(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody ClienteDTO dto, HttpServletResponse response) {
        Cliente clienteSalvo = clienteService.criarCliente(dto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EmptyResultDataAccessException(1);
        }
        clienteRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.atualizar(id, cliente);


        ClienteDTO responseDTO = new ClienteDTO(
                clienteSalvo.getId(),
                clienteSalvo.getNome(),
                clienteSalvo.getDocumento(),
                clienteSalvo.getCelular(),
                clienteSalvo.isAtivo(),
                clienteSalvo.getTelefone(),
                clienteSalvo.getDataCadastro(),
                new EnderecoDTO(
                        clienteSalvo.getEndereco().getRua(),
                        clienteSalvo.getEndereco().getNumero(),
                        clienteSalvo.getEndereco().getCidade(),
                        clienteSalvo.getEndereco().getEstado(),
                        clienteSalvo.getEndereco().getCep(),
                        clienteSalvo.getEndereco().getBairro()
                )
        );

        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
        clienteService.atualizarPropriedadeAtivo(id,ativo);
    }

    @GetMapping("/{clienteId}/usuarios")
    public ResponseEntity<PaginacaoDTO<UsuarioDTO>> listarUsuariosDoCliente(
            @PathVariable Long clienteId,
            Pageable pageable) {

        PaginacaoDTO<UsuarioDTO> usuarios = userService.getUsuariosByCliente(clienteId, pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/{clienteId}/usuarios")
    public ResponseEntity<UsuarioResponseDTO> criarUsuarioParaCliente(
            @PathVariable Long clienteId,
            @Valid @RequestBody UsuarioDTO dto) {

        StringBuilder senhaGerada = new StringBuilder();
        Usuario usuario = userService.criarUsuarioParaCliente(clienteId, dto, senhaGerada);

        return ResponseEntity.ok(new UsuarioResponseDTO(
                usuario.getUserId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getRoles().toString(),
                usuario.getUltimoLogin().toString(),
                senhaGerada.toString()
        ));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        userService.excluirUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }



}