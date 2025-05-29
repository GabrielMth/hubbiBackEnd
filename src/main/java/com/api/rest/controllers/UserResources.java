package com.api.rest.controllers;

import com.api.rest.dto.CreateUserDTO;
import com.api.rest.model.Role;
import com.api.rest.model.Usuario;
import com.api.rest.repository.RoleRepository;
import com.api.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UserResources {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResources(BCryptPasswordEncoder encoder) {
        this.passwordEncoder = encoder;
    }

    @Transactional
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarUser (@RequestBody CreateUserDTO dto) {

        var basicRole = roleRepository.findByName(Role.Values.MEMBRO.name());

        var userFromDb = userRepository.findByUsername(dto.username());
        if  (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new Usuario();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<Usuario>> listarUsuarios () {
        var listUsuarios = userRepository.findAll();

        return ResponseEntity.ok(listUsuarios);
    }
}