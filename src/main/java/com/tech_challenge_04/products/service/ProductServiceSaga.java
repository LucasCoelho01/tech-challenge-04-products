package com.tech_challenge_04.products.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tech_challenge_04.products.config.RabbitMQConfig;
import com.tech_challenge_04.products.entity.Product;
import com.tech_challenge_04.products.entity.dtos.ProductRequestDto;
import com.tech_challenge_04.products.entity.dtos.ProductResponseDto;
import com.tech_challenge_04.products.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceSaga {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PRODUCT_REQUEST)
    public void handleCustomerMessage(String jsonRequest) {
        Gson gson = new Gson();
        ProductRequestDto requestDto = gson.fromJson(jsonRequest, ProductRequestDto.class);

        List<Product> productsList = new ArrayList<>();

        requestDto.getProducts().forEach(productName -> {
            System.out.println("Product: " + productName);
            productsList.add(productRepository.findByName(productName));
        });

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setOrderId(requestDto.getOrderId());
        productResponseDto.setProducts(productsList);

        String json = gson.toJson(productResponseDto);

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_PRODUCT_RESPONSE, json);
    }
}
