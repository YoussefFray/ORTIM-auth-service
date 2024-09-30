package com.auth.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "a1e68365a6ac1ddc4f456b0f7adc126a4e0794637bd6e18cfa8ab920805b7859e2cf168064db4684906a37ddf00797f2f24d62e4115c75c4efe88d225ffc468fc75d5ee5cdc26c53552a606695c33ec1ac0223a3a01db7b7af094bf5f004a0efde4c0affc86aa4fd8b7de9a81d3b06e4d45805e8c75773c9b42baebaf521df69ce38e5cf6be9533f78b5ac8e720c43f0e2fcf2036ec86e50319c2110ab8598c6c8013c7d5a2ef791968a391a7f58d2bbaaf91bfb73b18fe680f4a04e30e9f1e6458c930cd8f2d928e4920c31d6a3fcc382d69f2c38a54428e9fea135d710c9e8a6b195a40afbdd466165c32985806b8c5f231377e5968e3322a100bcee341ec0";  // Ensure this is sufficiently long (at least 256 bits)
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());  // Create SecretKey

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()  // Use parserBuilder() instead of parser()
                .setSigningKey(secretKey)  // Use the SecretKey object
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Token valid for 10 hours
                .signWith(secretKey, SignatureAlgorithm.HS256)  // Use the new signWith() method
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
