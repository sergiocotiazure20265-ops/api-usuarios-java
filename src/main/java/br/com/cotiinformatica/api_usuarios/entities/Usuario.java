package br.com.cotiinformatica.api_usuarios.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String senha;

    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao;

    @ManyToOne //Muitos usuários para 1 Perfil
    @JoinColumn(name = "perfil_id", nullable = false) //Chave estrangeira
    private Perfil perfil;
}
