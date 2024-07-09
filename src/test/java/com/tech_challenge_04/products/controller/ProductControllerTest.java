package com.tech_challenge_04.products.controller;

import com.tech_challenge_04.products.entity.Product;
import com.tech_challenge_04.products.entity.dtos.ProductDto;
import com.tech_challenge_04.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto("Pastel", new BigDecimal(15.0), "LANCHE");
        product = new Product(productDto);
    }

    @Test
    void createProduct_success() {
        when(productService.createProduct(any(ProductDto.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(productDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).createProduct(any(ProductDto.class));
    }

    @Test
    void getAllProducts_success() {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void findByName_success() {
        String name = "testProduct";
        when(productService.findByName(name)).thenReturn(product);

        ResponseEntity<Product> response = productController.findByName(name);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).findByName(name);
    }

    @Test
    void updateProduct_success() {
        String name = "testProduct";
        when(productService.updateProduct(eq(name), any(ProductDto.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(name, productDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).updateProduct(eq(name), any(ProductDto.class));
    }

    @Test
    void deleteProduct_success() {
        String name = "testProduct";
        doNothing().when(productService).deleteProduct(name);

        ResponseEntity<?> response = productController.deleteProduct(name);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(name);
    }
}
