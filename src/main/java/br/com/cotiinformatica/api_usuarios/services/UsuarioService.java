package br.com.cotiinformatica.api_usuarios.services;

import br.com.cotiinformatica.api_usuarios.components.CryptoComponent;
import br.com.cotiinformatica.api_usuarios.components.JwtTokenComponent;
import br.com.cotiinformatica.api_usuarios.dtos.*;
import br.com.cotiinformatica.api_usuarios.entities.Perfil;
import br.com.cotiinformatica.api_usuarios.entities.Usuario;
import br.com.cotiinformatica.api_usuarios.exceptions.AcessoNegadoException;
import br.com.cotiinformatica.api_usuarios.exceptions.EmailJaCadastradoException;
import br.com.cotiinformatica.api_usuarios.repositories.PerfilRepository;
import br.com.cotiinformatica.api_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private CryptoComponent cryptoComponent;

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    /*
        Método para criar um usuário no sistema.
     */
    public CriarUsuarioResponse criar(CriarUsuarioRequest request) {

        //Verificar se o email já está cadastrado..
        if(usuarioRepository.findByEmail(request.email()) != null) {
            throw new EmailJaCadastradoException("Este email já está cadastrado para outro usuário.");
        }

        //Criptografar a senha do usuário..
        var senhaCriptografada = cryptoComponent.criptografarSenha(request.senha());

        //Verificar se o perfil 'Operador' existe no banco de dados
        var perfil = perfilRepository.findByNome("Operador");
        if(perfil == null) {

            perfil = new Perfil();
            perfil.setNome("Operador");

            perfilRepository.save(perfil);
        }

        //Capturando os dados do usuário
        var usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(senhaCriptografada);
        usuario.setPerfil(perfil);
        usuario.setDataHoraCriacao(LocalDateTime.now());

        //Salvar o usuário no banco de dados
        usuarioRepository.save(usuario);

        //Retornar a resposta
        return new CriarUsuarioResponse(
             "Usuário cadastrado com sucesso",
             usuario.getId(),
             usuario.getNome(),
             usuario.getEmail(),
             usuario.getDataHoraCriacao(),
             usuario.getPerfil().getNome()
        );
    }

    /*
        Método para autenticar o usuário
     */
    public AutenticarUsuarioResponse autenticar(AutenticarUsuarioRequest request) {

        //Criptografando a senha informada
        var senhaCriptografada = cryptoComponent.criptografarSenha(request.senha());

        //Buscando o usuário no banco de dados
        var usuario = usuarioRepository.findByEmailAndSenha(request.email(), senhaCriptografada);

        //Verificando se nenhum usuário foi encontrado
        if(usuario == null) {
            throw new AcessoNegadoException("Acesso negado. Credenciais inválidas.");
        }

        //Gerar o TOKEN JWT do usuário
        var accessToken = jwtTokenComponent.gerarToken(usuario.getEmail(), usuario.getPerfil().getNome());

        //Retornar a resposta da API
        return new AutenticarUsuarioResponse(
             "Usuário autenticado com sucesso",
             usuario.getId(),
             usuario.getNome(),
             usuario.getEmail(),
             usuario.getPerfil().getNome(),
             LocalDateTime.now(),
             accessToken
        );
    }

    public DadosUsuarioResponse consultarDados(String email) {

        var usuario = usuarioRepository.findByEmail(email);

        if(usuario == null) {
            throw new AcessoNegadoException("Usuário não encontrado.");
        }

        return new DadosUsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil().getNome(),
            usuario.getDataHoraCriacao()
        );
    }
}











