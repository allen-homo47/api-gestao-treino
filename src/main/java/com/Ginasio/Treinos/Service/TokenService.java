package com.Ginasio.Treinos.Service;
import com.Ginasio.Treinos.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret:123456789minhasecretsegura}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Ginasio Treinos")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withClaim("tipo", usuario.getTipoUser() != null ? usuario.getTipoUser().name() : "")
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Ginasio Treinos")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            // Token inválido ou expirado
            return null;
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("+02:00"));
    }
}