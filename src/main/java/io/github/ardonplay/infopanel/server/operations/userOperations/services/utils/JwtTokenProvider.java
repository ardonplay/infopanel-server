package io.github.ardonplay.infopanel.server.operations.userOperations.services.utils;


import io.github.ardonplay.infopanel.server.common.entities.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        // payload
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getUserRole().getName());

        var issuedDate = new Date();
        var expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secret = Base64.getEncoder().encode(secretKey.getBytes());
        Key signingKey = new SecretKeySpec(secret, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(user.getUsername()) // usually it's username
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).get("username", String.class);
    }

    public Integer getId(String token) {
        return getAllClaimsFromToken(token).get("id", Integer.class);
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encode(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
