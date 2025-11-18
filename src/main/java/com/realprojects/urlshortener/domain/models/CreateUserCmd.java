package com.realprojects.urlshortener.domain.models;

public record CreateUserCmd(
        String email,
        String password,
        String name,
        Role role) {
}
