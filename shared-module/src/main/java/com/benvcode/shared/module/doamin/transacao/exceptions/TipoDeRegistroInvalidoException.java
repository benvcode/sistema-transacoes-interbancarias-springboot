package com.benvcode.shared.module.doamin.transacao.exceptions;

public class TipoDeRegistroInvalidoException extends RuntimeException {
    public TipoDeRegistroInvalidoException(String message, Object recordObj) {
        super(message);
    }
}