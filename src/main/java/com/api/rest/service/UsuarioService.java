package com.api.rest.service;

import com.api.rest.dto.PaginacaoDTO;
import com.api.rest.dto.UsuarioDTO;
import com.api.rest.model.Cliente;
import com.api.rest.model.Usuario;
import com.api.rest.repository.ClienteRepository;
import com.api.rest.repository.RoleRepository;
import com.api.rest.repository.TaskMovementRepository;
import com.api.rest.repository.UserRepository;
import com.api.rest.service.exceptionDeRegraDeNegocio.SenhaAtualIncorretaException;
import com.api.rest.service.exceptionDeRegraDeNegocio.UsuarioTaskMovimentoException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Set;


@Service
public class UsuarioService {

    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskMovementRepository taskMovementRepository;

    public UsuarioService(UserRepository userRepository,
                          ClienteRepository clienteRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          TaskMovementRepository taskMovementRepository) {
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskMovementRepository = taskMovementRepository;
    }


    public PaginacaoDTO<UsuarioDTO> getUsuariosByCliente(Long clienteId, Pageable pageable) {
        Page<Usuario> usuariosPage = userRepository.findByClienteId(clienteId, pageable);

        Page<UsuarioDTO> dtoPage = usuariosPage.map(usuario -> new UsuarioDTO(
                usuario.getUserId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getRoles() != null ? usuario.getRoles().toString() : "Sem Perfil",
                formatarUltimoLogin(usuario.getUltimoLogin())
        ));

        return new PaginacaoDTO<>(dtoPage);
    }


    public String formatarUltimoLogin(Instant ultimoLogin) {
        if (ultimoLogin == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(ultimoLogin);
    }


    public Usuario criarUsuarioParaCliente(Long clienteId, UsuarioDTO dto, StringBuilder senhaGeradaOut) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        }

        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Já existe um usuário com este username.");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        String senhaGerada = gerarSenhaAleatoria(6);
        senhaGeradaOut.append(senhaGerada);

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setUsername(dto.username());
        usuario.setEmail(dto.email());
        usuario.setPassword(passwordEncoder.encode(senhaGerada));
        usuario.setCliente(cliente);
        usuario.setRoles(Set.of(roleRepository.findByName(dto.role())));

        Instant dataAntiga = LocalDate.of(1999, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        usuario.setUltimoLogin(dataAntiga);

        return userRepository.save(usuario);
    }


    private String gerarSenhaAleatoria(int tamanho) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            senha.append(letras.charAt(random.nextInt(letras.length())));
        }

        return senha.toString();
    }


    public void atualizarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado, contato o Desenvolvedor"));

        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new SenhaAtualIncorretaException("Senha atual incorreta");
        }

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        userRepository.save(usuario);
    }


    @Transactional
    public void excluirUsuarioPorId(Long id) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        if (taskMovementRepository.existsByMovimentadoPor(usuario)) {
            throw new UsuarioTaskMovimentoException();
        }

        usuario.getRoles().clear();
        userRepository.delete(usuario);
    }

}



