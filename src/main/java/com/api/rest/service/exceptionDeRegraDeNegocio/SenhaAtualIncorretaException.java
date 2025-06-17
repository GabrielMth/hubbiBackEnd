package com.api.rest.service.exceptionDeRegraDeNegocio;

public class SenhaAtualIncorretaException extends RuntimeException {
    public SenhaAtualIncorretaException(String message) {
        super("Ops! Senha atual incorreta.");
    }
}
