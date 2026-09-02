package com.mycompany.taskmanager.exception;

public class TarefaNaoEncontradaException extends RuntimeException {

    public TarefaNaoEncontradaException(Long id) {
        super("Não encontramos tarefas com o ID: " + id);
    }
}