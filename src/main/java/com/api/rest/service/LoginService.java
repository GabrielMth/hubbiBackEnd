package com.api.rest.service;

import com.api.rest.dto.loginDto.LoginRequestDTO;
import com.api.rest.dto.loginDto.LoginResponseDTO;
import com.api.rest.model.Role;
import com.api.rest.model.Usuario;
import com.api.rest.repository.UserRepository;
import com.api.rest.service.exceptionDeRegraDeNegocio.ClienteInativoException;
import com.api.rest.service.exceptionDeRegraDeNegocio.UsuarioInativoException;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;


    private final Map<String, TokenData> refreshTokenCache = new HashMap<>();

    private static final long ACCESS_TOKEN_EXPIRE_IN = 3600L;
    private static final long REFRESH_TOKEN_EXPIRE_IN = 18000L;
    private static final long CLEANUP_INTERVAL = 1000L;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        var userOpt = userRepository.findByUsername(loginRequest.username());

        if (userOpt.isEmpty() || !userOpt.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Usuário ou senha inválidos!");
        }

        var user = userOpt.get();

        if (!user.isAtivo()) {
            throw new UsuarioInativoException("Usuário inativo! Entre em contato com os consultores.");
        }

        if (user.getCliente() == null || !user.getCliente().isAtivo()) {
            throw new ClienteInativoException("Sua empresa está inativa! Entre em contato com os consultores.");
        }

        Instant now = Instant.now();

        user.atualizarUltimoLogin();
        userRepository.save(user);


        JwtClaimsSet accessClaims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRE_IN))
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();


        String refreshToken = UUID.randomUUID().toString();
        refreshTokenCache.put(refreshToken, new TokenData(user.getUserId(), System.currentTimeMillis()));

        return new LoginResponseDTO(accessToken, ACCESS_TOKEN_EXPIRE_IN, "Bearer", user.getUsername(),user.getNome(), refreshToken);
    }

    public String refreshToken(String refreshToken) {
        TokenData tokenData = refreshTokenCache.get(refreshToken);

        if (tokenData == null || tokenExpirado(tokenData.timestamp())) {
            throw new BadCredentialsException("Refresh token inválido ou expirado!");
        }

        var userOpt = userRepository.findById(tokenData.userId());
        if (userOpt.isEmpty()) {
            throw new BadCredentialsException("Usuário não encontrado.");
        }

        var user = userOpt.get();
        Instant now = Instant.now();

        JwtClaimsSet accessClaims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRE_IN))
                .claim("name", user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();
    }

    private boolean tokenExpirado(long timestamp) {
        long now = System.currentTimeMillis();
        return (now - timestamp) > REFRESH_TOKEN_EXPIRE_IN * 1000;
    }

    private void cleanupExpiredTokens() {
        long now = System.currentTimeMillis();
        refreshTokenCache.entrySet().removeIf(entry -> tokenExpirado(entry.getValue().timestamp()));
    }

    @PostConstruct
    public void startCleanupScheduler() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                this::cleanupExpiredTokens,
                CLEANUP_INTERVAL,
                CLEANUP_INTERVAL,
                TimeUnit.SECONDS
        );
    }


    private record TokenData(Long userId, long timestamp) {}
}
