package com.auth.auth_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<Token>> getAllTokens() {
        return ResponseEntity.ok(tokenService.getAllTokens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Token> getTokenById(@PathVariable UUID id) {
        Optional<Token> token = tokenService.getTokenById(id);
        return token.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Token> createToken(@RequestBody Token token) {
        return ResponseEntity.ok(tokenService.createToken(token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Token> updateToken(@PathVariable UUID id, @RequestBody Token newTokenData) {
        return ResponseEntity.ok(tokenService.updateToken(id, newTokenData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable UUID id) {
        tokenService.deleteToken(id);
        return ResponseEntity.noContent().build();
    }
}
