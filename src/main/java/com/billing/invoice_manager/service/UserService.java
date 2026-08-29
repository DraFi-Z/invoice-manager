package com.billing.invoice_manager.service;

import com.billing.invoice_manager.entity.User;
import com.billing.invoice_manager.exception.DuplicateResourceException;
import com.billing.invoice_manager.exception.ResourceNotFoundException;
import com.billing.invoice_manager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        log.info("Creating user with email: {}", user.getEmail());
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Duplicate user creation attempted for email: {}", user.getEmail());
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User saved = userRepository.save(user);
        log.info("User created successfully with id: {}", saved.getId());
        return saved;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        existing.setFullName(updatedUser.getFullName());
        existing.setEmail(updatedUser.getEmail());
        existing.setRole(updatedUser.getRole());
        existing.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(existing);
    }

    public void deactivateUser(Long id) {
        log.info("Deactivating user with id: {}", id);
        User existing = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User", "id", id);
                });
        existing.setIsActive(false);
        existing.setUpdatedAt(LocalDateTime.now());
        userRepository.save(existing);
        log.info("User deactivated successfully with id: {}", id);
    }
}