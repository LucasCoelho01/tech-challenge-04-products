package com.tech_challenge_04.products.entity.dtos;

import com.tech_challenge_04.products.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDto {
    private String orderId;
    private List<Product> products;
}
