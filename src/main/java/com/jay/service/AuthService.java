package com.jay.service;

import com.jay.dto.AuthRequest;
import com.jay.dto.AuthResponse;
import com.jay.dto.RegisterRequest;
import com.jay.security.CustomUserDetailsService;
import com.jay.security.Role;
import com.jay.security.UserAccount;
import com.jay.security.UserStore;
import com.jay.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(UserStore userStore,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       CustomUserDetailsService userDetailsService) {
        this.userStore = userStore;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public String register(RegisterRequest request) {
        if (userStore.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        Role role = Role.ROLE_USER;
        if (request.role() != null && !request.role().isBlank()) {
            role = Role.valueOf(request.role().trim().toUpperCase());
        }

        UserAccount account = new UserAccount(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                role
        );

        userStore.save(account);
        return "User registered successfully";
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.email());

        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}