package com.jay.security;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStore {

    private final Map<String, UserAccount> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public UserStore(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seed() {
        users.put(
                "user@example.com",
                new UserAccount(
                        "Default User",
                        "user@example.com",
                        passwordEncoder.encode("password"),
                        Role.ROLE_USER
                )
        );

        users.put(
                "admin@example.com",
                new UserAccount(
                        "Admin User",
                        "admin@example.com",
                        passwordEncoder.encode("admin123"),
                        Role.ROLE_ADMIN
                )
        );
    }

    public Optional<UserAccount> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(email.toLowerCase(Locale.ROOT)));
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public synchronized void save(UserAccount account) {
        users.put(account.email().toLowerCase(Locale.ROOT), account);
    }
}