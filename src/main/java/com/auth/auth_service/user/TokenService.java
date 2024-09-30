package com.auth.auth_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public Optional<Token> getTokenById(UUID id) {
        return tokenRepository.findById(id);
    }

    public Token createToken(Token token) {
        return tokenRepository.save(token);
    }

    public void deleteToken(UUID id) {
        tokenRepository.deleteById(id);
    }

    public Token updateToken(UUID id, Token newTokenData) {
        return tokenRepository.findById(id)
                .map(token -> {
                    token.setRevoked(newTokenData.isRevoked());
                    return tokenRepository.save(token);
                }).orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
