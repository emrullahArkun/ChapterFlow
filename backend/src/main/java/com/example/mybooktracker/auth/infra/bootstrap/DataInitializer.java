package com.example.mybooktracker.auth.infra.bootstrap;

import com.example.mybooktracker.auth.domain.Role;
import com.example.mybooktracker.auth.domain.User;
import com.example.mybooktracker.auth.infra.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private static final String DEFAULT_ADMIN_EMAIL = "admin@example.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "AdminPassword123";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (!userRepository.existsByEmail(DEFAULT_ADMIN_EMAIL)) {
                User admin = new User();
                admin.setEmail(DEFAULT_ADMIN_EMAIL);
                admin.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                userRepository.save(admin);
                log.info("Default admin user created: {}", DEFAULT_ADMIN_EMAIL);
            }
        };
    }
}
