package com.tech_challenge_04.products.service;

import com.tech_challenge_04.products.entity.Product;
import com.tech_challenge_04.products.entity.dtos.ProductDto;
import com.tech_challenge_04.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto("Pastel", new BigDecimal(15.0), "LANCHE");
        product = new Product(productDto);
    }

    @Test
    void createProduct_success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(productDto);

        assertNotNull(createdProduct);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProducts_success() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findByName_success() {
        String name = "testProduct";
        when(productRepository.findByName(name)).thenReturn(product);

        Product foundProduct = productService.findByName(name);

        assertNotNull(foundProduct);
        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findByName(name);
    }

    @Test
    void updateProduct_success() {
        String name = "testProduct";
        when(productRepository.findByName(name)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(name, productDto);

        assertNotNull(updatedProduct);
        verify(productRepository, times(1)).findByName(name);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_notFound() {
        String name = "testProduct";
        when(productRepository.findByName(name)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(name, productDto));

        verify(productRepository, times(1)).findByName(name);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct_success() {
        String name = "testProduct";
        when(productRepository.findByName(name)).thenReturn(product);

        productService.deleteProduct(name);

        verify(productRepository, times(1)).findByName(name);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_notFound() {
        String name = "testProduct";
        when(productRepository.findByName(name)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(name));

        verify(productRepository, times(1)).findByName(name);
        verify(productRepository, never()).delete(any(Product.class));
    }
}
