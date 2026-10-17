package br.com.cotiinformatica.api_usuarios.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenComponent {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /*
        Método para gerar os tokens do usuário
     */
    public String gerarToken(String email, String perfil) {

        //Data e hora atual
        var agora = new Date();

        //Calcular a data e hora de expiração do token
        var expiracao = new Date(
            agora.getTime() + expiration
        );

        //Construir e retornar o TOKEN JWT:
        return Jwts.builder()
                .setSubject(email) //identificação do usuário
                .claim("perfil", perfil) //perfil do usuário
                .setIssuedAt(agora) //data e hora de geração do token
                .setExpiration(expiracao) //data e hora de expiração
                .signWith( //assinatura do token
                    SignatureAlgorithm.HS256,
                    secret.getBytes(StandardCharsets.UTF_8)
                )
                .compact(); //finalizar e retornar o token
    }

    /*
        Método para validar um token JWT e retornar os seus dados
     */
    public Claims validarToken(String token) {

        return Jwts.parser()
                .setSigningKey(
                        secret.getBytes(StandardCharsets.UTF_8)
                )
                .parseClaimsJws(token)
                .getBody();
    }

}
