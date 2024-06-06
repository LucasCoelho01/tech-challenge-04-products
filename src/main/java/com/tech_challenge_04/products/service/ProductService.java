package com.tech_challenge_04.products.service;

import com.tech_challenge_04.products.entity.Product;
import com.tech_challenge_04.products.entity.dtos.ProductDto;
import com.tech_challenge_04.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDto productDto) {
        return productRepository.save(new Product(productDto));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product updateProduct(String name, ProductDto productDto) {
        var product = productRepository.findByName(name);

        if (Objects.isNull(product)) {
            throw new EntityNotFoundException();
        }

        product.updateProduct(productDto);

        return productRepository.save(product);
    }

    public void deleteProduct(String name) {
        var product = productRepository.findByName(name);

        if (Objects.isNull(product)) {
            throw new EntityNotFoundException();
        }

        productRepository.delete(product);
    }
}
