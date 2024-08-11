package com.tech_challenge_04.products.entity.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDto {
    private String orderId;
    private List<String> products;

    public ProductRequestDto() {}

    public ProductRequestDto(String orderId, List<String> products) {
        this.orderId = orderId;
        this.products = products;
    }
}
