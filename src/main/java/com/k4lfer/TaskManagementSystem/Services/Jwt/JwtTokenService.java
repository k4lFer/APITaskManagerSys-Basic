package com.k4lfer.TaskManagementSystem.Services.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    // Generación del Access Token
    public String generateAccessToken(UUID userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, userId.toString(), 1000 * 60 * 60 * 24); // 1 día de validez
    }

    // Generación del Refresh Token
    public String generateRefreshToken(UUID userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, userId.toString(), 1000 * 60 * 60 * 24 * 7); // 7 días de validez
    }

    // Método para crear el token con los claims y la fecha de expiración
    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtener la clave para firmar los tokens
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Validación del token
    public boolean validateToken(String token) {
        try {
            Jwts
            .parser()
            .setSigningKey(getKey())
                        .build()
            .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Obtener el nombre de usuario del token
    public UUID getUserIdFromToken(String token) {
        return UUID.fromString(getClaim(token, Claims::getSubject));
    }

    public String getUserRoleFromToken(String token) {
        return getClaim(token, claims -> claims.get("role", String.class)); 
    }
    

    // Obtener un claim específico del token
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtener todos los claims del token
    @SuppressWarnings("deprecation")
    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken);
    }
    
    public String refreshAccessToken(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh Token is invalid or expired");
        }
        
        UUID userId = getUserIdFromToken(refreshToken);
        String role = getUserRoleFromToken(refreshToken);
        return generateAccessToken(userId, role);
    }
    
}
