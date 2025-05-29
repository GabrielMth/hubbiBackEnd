package com.api.rest.dto;

public record LoginResponseDTO(String accessToken, Long expireIn, String tokenType, String name, String refreshToken) {
}