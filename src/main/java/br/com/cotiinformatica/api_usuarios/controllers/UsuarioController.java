package br.com.cotiinformatica.api_usuarios.controllers;

import br.com.cotiinformatica.api_usuarios.dtos.*;
import br.com.cotiinformatica.api_usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("criar")
    public ResponseEntity<CriarUsuarioResponse> criar(@RequestBody @Valid CriarUsuarioRequest request) {
        var response = usuarioService.criar(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("autenticar")
    public ResponseEntity<AutenticarUsuarioResponse> autenticar(@RequestBody @Valid AutenticarUsuarioRequest request) {
        var response = usuarioService.autenticar(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("dados")
    public ResponseEntity<DadosUsuarioResponse> dados(Authentication authentication) {

        //Obter o email do usuário autenticado
        var email = authentication.getName();

        //Buscar o usuário através do email
        var response = usuarioService.consultarDados(email);

        //Retornar os dados do usuário
        return ResponseEntity.ok().body(response);
    }
}
