package br.com.cotiinformatica.api_usuarios.components;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class CryptoComponent {

    public String criptografarSenha(String senha) {

        try {
            //Criando o algoritmo SHA-256
            var messageDigest = MessageDigest.getInstance("SHA-256");

            //Gerando o hash da senha
            byte[] hash = messageDigest.digest(
                    senha.getBytes(StandardCharsets.UTF_8)
            );

            //Convertendo o hash para hexadecimal
            var resultado = new StringBuilder();

            for (byte b : hash) {
                resultado.append(
                        String.format("%02x", b)
                );
            }

            return resultado.toString();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao criptografar a senha.",e
            );
        }
    }
}
