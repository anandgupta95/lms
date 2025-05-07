package com.lms.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
@Value("${jwt.secret}")
private String secret;

    private SecretKey SECRET_KEY;

    @PostConstruct
    public void init() {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret is not set or too short! Must be at least 32 characters.");
        }
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refreshExpiration}")
    private long refreshExpiration;

    public String generateToken(Long id, String username, String role, boolean isRefreshToken) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .claim("role", role)
                .claim("userId", id)
                .expiration(new Date(System.currentTimeMillis() + (isRefreshToken ? refreshExpiration : jwtExpiration)))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public  String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    public  Long extractId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY).build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }




    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY).build()
                .parseSignedClaims(token)
                .getPayload();
    }

}

