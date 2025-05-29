package com.api.rest.service;

import com.api.rest.dto.ClienteRequestDTO;
import com.api.rest.dto.UsuarioDTO;
import com.api.rest.model.Cliente;
import com.api.rest.model.Endereco;
import com.api.rest.model.KanbanBoard;
import com.api.rest.model.Usuario;
import com.api.rest.repository.ClienteRepository;
import com.api.rest.repository.KanbanBoardRepository;
import com.api.rest.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private UserRepository usuarioRepository;
    @Autowired private KanbanBoardRepository kanbanBoardRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    public Cliente criarCliente(ClienteRequestDTO dto) {

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setDocumento(dto.documento());
        cliente.setCelular(dto.celular());
        cliente.setTelefone(dto.telefone());
        cliente.setAtivo(true);


        Endereco endereco = new Endereco(
                dto.endereco().rua(),
                dto.endereco().numero(),
                dto.endereco().bairro(),
                dto.endereco().cep(),
                dto.endereco().cidade(),
                dto.endereco().estado()
        );
        cliente.setEndereco(endereco);


        cliente = clienteRepository.save(cliente);


        KanbanBoard kanban = new KanbanBoard();
        kanban.setCliente(cliente);
        kanban = kanbanBoardRepository.save(kanban);
        cliente.setKanbanBoard(kanban);


        return clienteRepository.save(cliente);
    }


    public Usuario adicionarUsuario(Long clienteId, UsuarioDTO usuarioDTO) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->  new EmptyResultDataAccessException(1));


        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setUsername(usuarioDTO.username());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.password()));
        usuario.setCliente(cliente);  // Associar o usuário ao cliente


        usuario = usuarioRepository.save(usuario);

        return usuario;
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        Cliente clienteSalva = clienteRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        BeanUtils.copyProperties(cliente, clienteSalva, "id"); // Mantém o ID original
        return clienteRepository.save(clienteSalva);
    }

    public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
        Cliente clienteSalva = buscarClienteId(id);
        clienteSalva.setAtivo(ativo);
        clienteRepository.save(clienteSalva);
    }

    public Cliente buscarClienteId(Long id) {
        Cliente clienteSalva = clienteRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return clienteSalva;
    }

}