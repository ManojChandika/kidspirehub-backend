package com.kidspirehub.backend.service;

import com.kidspirehub.backend.model.User;
import com.kidspirehub.backend.repository.UserRepository;
import com.kidspirehub.backend.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public String signup(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .build();
        userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        return jwtService.generateToken(user.getEmail(), claims);
    }

    public String login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setLastLoginAt(java.time.Instant.now());
        userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        return jwtService.generateToken(user.getEmail(), claims);
    }
}


