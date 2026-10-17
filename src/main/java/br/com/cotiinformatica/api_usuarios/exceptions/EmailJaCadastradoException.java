package br.com.cotiinformatica.api_usuarios.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String message) {
        super(message);
    }
}
