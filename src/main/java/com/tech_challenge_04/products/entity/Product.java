package com.tech_challenge_04.products.entity;

import com.tech_challenge_04.products.entity.dtos.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private BigDecimal price;
    private String category;

    public Product() {}

    public Product(ProductDto productDto) {
        this.name = productDto.name();
        this.price = productDto.price();
        this.category = productDto.category();
    }

    public void updateProduct(ProductDto productDto) {
        this.name = productDto.name();
        this.price = productDto.price();
        this.category = productDto.category();
    }
}
