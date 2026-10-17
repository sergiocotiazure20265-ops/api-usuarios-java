package br.com.cotiinformatica.api_usuarios.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record CriarUsuarioResponse(
        String mensagem,
        UUID usuarioId,
        String nome,
        String email,
        LocalDateTime dataHoraCriacao,
        String perfil
) {
}
