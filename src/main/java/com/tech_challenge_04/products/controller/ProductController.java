package com.tech_challenge_04.products.controller;

import com.tech_challenge_04.products.entity.Product;
import com.tech_challenge_04.products.entity.dtos.ProductDto;
import com.tech_challenge_04.products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    ResponseEntity<Product> findByCpf(@PathVariable String name) {
        return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
    }

    @PutMapping("/{name}")
    @Transactional
    ResponseEntity<Product> updateProduct(@PathVariable String name, @RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(name, productDto), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    @Transactional
    ResponseEntity deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
        return new ResponseEntity(HttpStatus.OK);
    }
}
