package com.tech_challenge_04.products.entity.dtos;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductDto(
        @NotBlank
        String name,

        BigDecimal price,

        String category

) {}
