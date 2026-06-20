package com.billing.invoice_manager.service;

import com.billing.invoice_manager.entity.User;
import com.billing.invoice_manager.exception.InvalidOperationException;
import com.billing.invoice_manager.repository.UserRepository;
import com.billing.invoice_manager.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User authenticate(String email, String password) {
//        System.out.println("DEBUG: attempting login for email: " + email);
        log.info("Authentication attempt for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Authentication failed - user not found for email: {}", email);
                    return new InvalidOperationException("Invalid email or password");
                });

//        System.out.println("DEBUG: user found: " + user.getEmail());
//        System.out.println("DEBUG: is active: " + user.getIsActive());
//        System.out.println("DEBUG: stored hash: " + user.getPasswordHash());
//        System.out.println("DEBUG: password matches: " + passwordEncoder.matches(password, user.getPasswordHash()));

        if (!user.getIsActive()) {
            log.warn("Authentication failed - account deactivated for email: {}", email);
            throw new InvalidOperationException("Account is deactivated");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            log.warn("Authentication failed - wrong password for email: {}", email);
            throw new InvalidOperationException("Invalid email or password");
        }

        log.info("Authentication successful for email: {}", email);
        return user;

    }
}