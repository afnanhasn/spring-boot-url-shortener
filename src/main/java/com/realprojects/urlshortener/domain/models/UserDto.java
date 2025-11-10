package com.realprojects.urlshortener.domain.models;

import com.realprojects.urlshortener.domain.entities.User;

import java.io.Serializable;

/**
 * DTO for {@link com.realprojects.urlshortener.domain.entities.User}
 */
public record UserDto(Long id, String name) implements Serializable {
}