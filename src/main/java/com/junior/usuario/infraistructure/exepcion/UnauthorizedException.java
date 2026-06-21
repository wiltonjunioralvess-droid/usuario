package com.junior.usuario.infraistructure.exepcion;

import javax.security.sasl.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {


    public UnauthorizedException(String mensagem) {
        super(mensagem);
    }

    public UnauthorizedException(String mensagem, Throwable throwable) {
        super(mensagem);
    }
}

