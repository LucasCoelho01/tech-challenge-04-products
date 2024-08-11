package com.tech_challenge_04.products.config;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_PRODUCT_REQUEST = "productRequest";
    public static final String QUEUE_PRODUCT_RESPONSE = "productResponse";

    @Bean
    public Queue queue_customerRequest() {
        return new Queue(QUEUE_PRODUCT_REQUEST, true);
    }

    @Bean
    public Queue queue_customerResponse() {
        return new Queue(QUEUE_PRODUCT_RESPONSE, true);
    }
}
