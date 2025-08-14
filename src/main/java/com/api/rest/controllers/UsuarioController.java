package com.api.rest.controllers;

import com.api.rest.dto.loginDto.AtualizarSenhaDTO;
import com.api.rest.model.Usuario;
import com.api.rest.repository.RoleRepository;
import com.api.rest.repository.UserRepository;
import com.api.rest.service.ClienteService;
import com.api.rest.service.UsuarioService;
import com.api.rest.service.exceptionDeRegraDeNegocio.SenhaAtualIncorretaException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    private final ClienteService clienteService;

    public UsuarioController(BCryptPasswordEncoder encoder, UsuarioService usuarioService, RoleRepository roleRepository, UserRepository userRepository, ClienteService clienteService) {
        this.passwordEncoder = encoder;
        this.usuarioService = usuarioService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.clienteService = clienteService;
    }

    @PatchMapping("/senha")
    public ResponseEntity<Object> atualizarSenhaUsuario(
            @Valid @RequestBody AtualizarSenhaDTO dto,
            Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());

        try {
            usuarioService.atualizarSenha(userId, dto.getSenhaAtual(), dto.getNovaSenha());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(
                            Map.of("mensagemUsuario", "Usuário não encontrado.",
                                    "mensagemDesenvolvedor", e.getMessage())
                    ));
        } catch (SenhaAtualIncorretaException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(List.of(
                            Map.of("mensagemUsuario", e.getMessage())
                    ));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarUsuarios () {
        var listUsuarios = userRepository.findAll();

        return ResponseEntity.ok(listUsuarios);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Object> atualizarStatusUsuario(
            @PathVariable Long id,
            @RequestParam boolean ativo) {

        var usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuario.setAtivo(ativo);
        userRepository.save(usuario);

        String mensagem = ativo ? "Usuário ativado com sucesso." : "Usuário inativado com sucesso.";

        return ResponseEntity.ok(Map.of("mensagem", mensagem));
    }

}