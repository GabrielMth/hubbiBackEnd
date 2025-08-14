package com.api.rest.service.exceptionDeRegraDeNegocio;

public class UsuarioInativoException extends RuntimeException {
    public UsuarioInativoException(String message) {
        super(message);
    }
}