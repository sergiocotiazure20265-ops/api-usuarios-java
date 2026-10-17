package br.com.cotiinformatica.api_usuarios.repositories;

import br.com.cotiinformatica.api_usuarios.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, UUID> {

    /*
        Método para consultar 1 perfil pelo nome
     */
    Perfil findByNome(String nome);
}
