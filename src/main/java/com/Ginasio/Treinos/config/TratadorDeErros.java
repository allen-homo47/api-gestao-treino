package com.Ginasio.Treinos.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Devolve os erros em JSON ({"mensagem": "..."}) para o frontend poder mostrá-los
@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> credenciaisInvalidas() {
        return erro(HttpStatus.UNAUTHORIZED, "Utilizador ou password inválidos");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> dadosInvalidos(MethodArgumentNotValidException ex) {
        var campo = ex.getBindingResult().getFieldErrors().stream().findFirst();
        var mensagem = campo.map(f -> f.getField() + ": " + f.getDefaultMessage()).orElse("Dados inválidos");
        return erro(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> violacaoDeDados() {
        return erro(HttpStatus.CONFLICT, "Operação não permitida: o registo já existe ou está a ser usado por outro registo");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> erroGenerico(RuntimeException ex) {
        return erro(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Map<String, String>> erro(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("mensagem", mensagem != null ? mensagem : "Erro inesperado"));
    }
}
