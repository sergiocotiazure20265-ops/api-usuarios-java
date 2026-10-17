package br.com.cotiinformatica.api_usuarios.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record AutenticarUsuarioResponse(
        String mensagem,
        UUID usuarioId,
        String nome,
        String email,
        String perfil,
        LocalDateTime dataHoraAcesso,
        String accessToken
) {
}
