package com.auth.auth_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User updateUser(UUID id, User newUserData) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUserData.getName());
                    user.setEmail(newUserData.getEmail());
                    user.setPassword(newUserData.getPassword());
                    user.setRole(newUserData.getRole());
                    user.setStatus(newUserData.getStatus());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
