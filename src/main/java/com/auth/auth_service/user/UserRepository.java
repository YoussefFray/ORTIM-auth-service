package com.auth.auth_service.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // You can add custom query methods here
    Optional<User> findByEmail(String email); // Find user by email
    boolean existsByEmail(String email); // Check if user exists by email
}

