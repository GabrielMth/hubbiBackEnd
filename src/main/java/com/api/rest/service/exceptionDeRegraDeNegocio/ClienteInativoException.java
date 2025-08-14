package com.api.rest.service.exceptionDeRegraDeNegocio;

public class ClienteInativoException extends RuntimeException {
    public ClienteInativoException(String message) {
        super(message);
    }
}