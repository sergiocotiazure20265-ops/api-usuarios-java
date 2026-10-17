package br.com.cotiinformatica.api_usuarios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 6, max = 150, message = "O nome deve ter entre 6 e 150 caracteres.")
        String nome,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "Informe um endereço de email válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*!?]).{8,20}$",
                message = "A senha deve ter entre 8 e 20 caracteres, contendo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
        )
        String senha
) {
}
