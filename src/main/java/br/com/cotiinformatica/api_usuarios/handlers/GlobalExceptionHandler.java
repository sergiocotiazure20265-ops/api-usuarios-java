package br.com.cotiinformatica.api_usuarios.handlers;

import br.com.cotiinformatica.api_usuarios.exceptions.AcessoNegadoException;
import br.com.cotiinformatica.api_usuarios.exceptions.EmailJaCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Tratamento dos erros de validação dos DTOs
     * Exemplo: @NotBlank, @Email, @Size, @Pattern etc.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException e) {

        var response = new HashMap<String, Object>();

        var errors = new HashMap<String, String>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("erro", "Erro de validação.");
        response.put("campos", errors);
        response.put("dataHora", LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /*
     * Tratamento de erros para EmailJaCadastradoException
     */
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> handleEmailJaCadastradoException(
            Exception e) {

        var response = new HashMap<String, Object>();

        response.put("status", HttpStatus.CONFLICT.value());
        response.put("erro", "Conflito.");
        response.put("mensagem", e.getMessage());
        response.put("dataHora", LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    /*
     * Tratamento de erros para AcessoNegadoException
     */
    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<Map<String, Object>> handleAcessoNegadoException(
            Exception e) {

        var response = new HashMap<String, Object>();

        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("erro", "Não autorizado.");
        response.put("mensagem", e.getMessage());
        response.put("dataHora", LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    /*
     * Tratamento de erros internos da aplicação
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(
            Exception e) {

        var response = new HashMap<String, Object>();

        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("erro", "Erro interno do servidor.");
        response.put("mensagem", e.getMessage());
        response.put("dataHora", LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}