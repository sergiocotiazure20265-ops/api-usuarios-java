package br.com.cotiinformatica.api_usuarios.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(length = 50, nullable = false)
    private String sistema;
}
