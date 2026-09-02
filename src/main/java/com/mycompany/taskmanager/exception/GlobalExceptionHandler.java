package com.mycompany.taskmanager.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> tratarTarefaNaoEncontrada(
            TarefaNaoEncontradaException ex) {

        return criarResposta(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    public ResponseEntity<Map<String, Object>> tratarUsuarioNaoAutorizado(
            UsuarioNaoAutorizadoException ex) {

        return criarResposta(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .orElse("Dados inválidos");

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                mensagem
        );
    }

    @ExceptionHandler(EmailCadastradoException.class)
    public ResponseEntity<Map<String, Object>> tratarEmailCadastrado(
        EmailCadastradoException ex) {

    return criarResposta(
            HttpStatus.CONFLICT,
            ex.getMessage()
    );
}
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<Map<String, Object>> tratarCredenciaisInvalidas(
            CredenciaisInvalidasException ex) {

        return criarResposta(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> tratarAcessoNegado(
        AccessDeniedException ex) {

    return criarResposta(
            HttpStatus.FORBIDDEN,
            "Usuário não autorizado"
    );
}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErroGeral(
            Exception ex) {

        return criarResposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno no servidor"
        );
    }

    private ResponseEntity<Map<String, Object>> criarResposta(
            HttpStatus status,
            String mensagem) {

        Map<String, Object> erro = new LinkedHashMap<>();

        erro.put("timestamp", LocalDateTime.now());
        erro.put("status", status.value());
        erro.put("erro", status.getReasonPhrase());
        erro.put("mensagem", mensagem);

        return ResponseEntity
                .status(status)
                .body(erro);
    }
}