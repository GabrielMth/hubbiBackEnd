package com.api.rest.controllers;

import com.api.rest.dto.AtualizarSenhaDTO;
import com.api.rest.dto.CreateUserDTO;
import com.api.rest.model.Role;
import com.api.rest.model.Usuario;
import com.api.rest.repository.RoleRepository;
import com.api.rest.repository.UserRepository;
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
import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public UsuarioController(BCryptPasswordEncoder encoder, UsuarioService usuarioService, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = encoder;
        this.usuarioService = usuarioService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<Usuario>> listarUsuarios () {
        var listUsuarios = userRepository.findAll();

        return ResponseEntity.ok(listUsuarios);
    }
}