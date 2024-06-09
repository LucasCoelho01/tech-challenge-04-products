package com.tech_challenge_04.products.repository;

import com.tech_challenge_04.products.entity.Product;
import com.tech_challenge_04.products.entity.dtos.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        ProductDto productDto = new ProductDto("Pastel", new BigDecimal(15.0), "LANCHE");
        product = new Product(productDto);
        product.setId(UUID.randomUUID());
    }

    @Test
    void saveProduct_success() {
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
    }

    @Test
    void findByName_success() {
        productRepository.save(product);

        Product foundProduct = productRepository.findByName(product.getName());

        assertNotNull(foundProduct);
        assertEquals(product.getName(), foundProduct.getName());
    }

    @Test
    void findAllProducts_success() {
        productRepository.save(product);

        List<Product> products = productRepository.findAll();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void deleteProduct_success() {
        productRepository.save(product);
        productRepository.delete(product);

        Optional<Product> deletedProduct = productRepository.findById(product.getId());

        assertTrue(deletedProduct.isEmpty());
    }

    @Test
    void findById_success() {
        Product savedProduct = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals(savedProduct.getName(), foundProduct.get().getName());
    }
}
