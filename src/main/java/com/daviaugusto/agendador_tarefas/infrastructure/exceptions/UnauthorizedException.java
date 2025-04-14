package com.daviaugusto.agendador_tarefas.infrastructure.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {

    public UnauthorizedException(String msg){
        super(msg);
    }

    public UnauthorizedException(String msg, Throwable throwable){
        super(msg);
    }
}
