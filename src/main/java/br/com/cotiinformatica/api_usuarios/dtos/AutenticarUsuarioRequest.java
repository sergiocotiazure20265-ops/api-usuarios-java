package br.com.cotiinformatica.api_usuarios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutenticarUsuarioRequest(

        @NotBlank(message = "O email de acesso é obrigatório.")
        @Email(message = "Informe um endereço de email de acesso válido.")
        String email,

        @Size(min = 8, message = "A senha de acesso deve ter pelo menos 8 caracteres.")
        @NotBlank(message = "A senha de acesso é obrigatória.")
        String senha
) {
}
