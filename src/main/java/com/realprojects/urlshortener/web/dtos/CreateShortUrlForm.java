package com.realprojects.urlshortener.web.dtos;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlForm(
        @NotBlank(message = "Original URL is required")
        String originalUrl,
        Boolean isPrivate,
        @Min(1)//These annotations provide optional values
        @Max(365)// as well which can be chosen by logged in users
        Integer expirationInDays
        ) {
}
