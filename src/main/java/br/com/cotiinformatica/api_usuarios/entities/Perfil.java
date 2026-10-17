package br.com.cotiinformatica.api_usuarios.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(length = 50, nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(
        name = "perfil_permissao",
        joinColumns = @JoinColumn(name = "perfil_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private List<Permissao> permissoes;
}
