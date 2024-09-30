package com.auth.auth_service.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    // You can add custom query methods here to find tokens based on user or token string
    Optional<Token> findByToken(String token);

    void deleteByUser(User user);
}

