package br.com.cotiinformatica.api_usuarios.security;

import br.com.cotiinformatica.api_usuarios.components.JwtTokenComponent;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication
        .UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority
        .SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenComponent jwtTokenComponent;

    public JwtAuthenticationFilter(
            JwtTokenComponent jwtTokenComponent) {

        this.jwtTokenComponent = jwtTokenComponent;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Capturar o token enviado no cabeçalho
        String authorization =
                request.getHeader("Authorization");

        // Verificar se foi enviado um Bearer Token
        if (authorization != null &&
                authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            try {

                // Validar a assinatura e expiração do JWT
                var claims = jwtTokenComponent
                        .validarToken(token);

                // Obter o email do usuário autenticado
                String email = claims.getSubject();

                if (email == null || email.isBlank()) {
                    throw new IllegalArgumentException(
                            "Token sem identificação."
                    );
                }

                // Obter o perfil do usuário
                String perfil = claims.get(
                        "perfil", String.class
                );

                if (perfil == null || perfil.isBlank()) {
                    throw new IllegalArgumentException(
                            "Token sem perfil."
                    );
                }

                // Criar a autenticação do Spring Security
                var authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + perfil
                                        )
                                )
                        );

                // Registrar o usuário autenticado
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

            } catch (JwtException | IllegalArgumentException e) {

                SecurityContextHolder.clearContext();

                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Token JWT inválido ou expirado."
                );

                return;
            }
        }

        // Continuar a execução da requisição
        filterChain.doFilter(request, response);
    }

    /*
     * Executar este filtro apenas no endpoint protegido.
     */
    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request) {

        return !request.getServletPath()
                .equals("/api/v1/usuarios/dados");
    }
}