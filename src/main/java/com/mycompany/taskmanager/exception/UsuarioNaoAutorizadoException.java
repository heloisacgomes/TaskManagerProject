package com.mycompany.taskmanager.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException {

    public UsuarioNaoAutorizadoException() {
        super("Usuário não autorizado a acessar esta tarefa");
    }

    public UsuarioNaoAutorizadoException(String mensagem) {
        super(mensagem);
    }
}