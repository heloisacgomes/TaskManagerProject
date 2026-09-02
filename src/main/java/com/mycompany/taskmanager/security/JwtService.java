package com.mycompany.taskmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String gerarToken(String email) {

        Date agora = new Date();
        Date expiracao = new Date(
                agora.getTime() + jwtExpiration
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(getChave())
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairClaim(
                token,
                Claims::getSubject
        );
    }

    public boolean tokenValido(
            String token,
            String email) {

        String emailToken = extrairEmail(token);

        return emailToken.equals(email)
                && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {

        Date expiracao = extrairClaim(
                token,
                Claims::getExpiration
        );

        return expiracao.before(new Date());
    }

    private <T> T extrairClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extrairTodosClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extrairTodosClaims(String token) {

        return Jwts.parser()
                .verifyWith(
                        (javax.crypto.SecretKey) getChave()
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getChave() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
