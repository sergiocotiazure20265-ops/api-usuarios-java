package br.com.cotiinformatica.api_usuarios.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosUsuarioResponse (
    UUID id,
    String nome,
    String email,
    String perfil,
    LocalDateTime dataHoraCriacao
) {

}
