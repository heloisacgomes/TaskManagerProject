package com.mycompany.taskmanager.exception;

public class EmailCadastradoException extends RuntimeException {

    public EmailCadastradoException() {
        super("E-mail já cadastrado");
    }
}
