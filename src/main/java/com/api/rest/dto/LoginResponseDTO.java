package com.api.rest.dto;

public record LoginResponseDTO(String accessToken, Long expireIn, String tokenType, String username, String name, String refreshToken) {
}