package com.benvcode.shared.module.doamin.transacao.exceptions;

public class CodigoAgenciaBancariaDesconhecidoException extends RuntimeException {
    public CodigoAgenciaBancariaDesconhecidoException(String message, String codigoAgenciaBancaria) {
        super(message);
    }
}