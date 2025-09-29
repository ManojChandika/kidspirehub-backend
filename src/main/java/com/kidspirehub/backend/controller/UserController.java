package com.kidspirehub.backend.controller;

import com.kidspirehub.backend.model.User;
import com.kidspirehub.backend.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        Profile res = new Profile();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        res.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
        res.setLastLoginAt(user.getLastLoginAt() != null ? user.getLastLoginAt().toString() : null);
        return ResponseEntity.ok(res);
    }

    @Data
    public static class Profile {
        private Long id;
        private String name;
        private String email;
        private String role;
        private String createdAt;
        private String lastLoginAt;
    }
}


