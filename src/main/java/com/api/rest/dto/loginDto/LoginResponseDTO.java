package com.api.rest.dto.loginDto;

public record LoginResponseDTO(String accessToken, Long expireIn, String tokenType, String username, String name, String refreshToken) {
}