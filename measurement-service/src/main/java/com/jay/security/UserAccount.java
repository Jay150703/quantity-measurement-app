package com.jay.security;

public record UserAccount(
        String name,
        String email,
        String password,
        Role role
) {
}