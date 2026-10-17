package br.com.cotiinformatica.api_usuarios.exceptions;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String message) {
        super(message);
    }
}
