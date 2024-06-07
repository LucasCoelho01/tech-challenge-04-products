package com.tech_challenge_04.products.bdd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_challenge_04.products.entity.Product;
import com.tech_challenge_04.products.entity.dtos.ProductDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CucumberStepDefinition {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private ProductDto productDto;

    @Autowired
    private ObjectMapper objectMapper;

    @Given("a product payload with name {string}, price {double} and category {string}")
    public void a_product_payload_with_name_price_and_category(String name, Double price, String category) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productDto = new ProductDto(name, new BigDecimal(price), category);
    }
    @When("the client requests to create a product")
    public void the_client_requests_to_create_a_product() throws Exception {
        mvcResult = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andReturn();
    }
    @Then("the response should contain the product's ID and details")
    public void the_response_should_contain_the_product_s_id_and_details() throws Exception {
        String content = mvcResult.getResponse().getContentAsString();
        Product createdCustomer = objectMapper.readValue(content, Product.class);
        assertThat(createdCustomer.getId()).isNotNull();
        assertThat(createdCustomer.getName()).isEqualTo(productDto.name());
        assertThat(createdCustomer.getPrice()).isEqualTo(productDto.price());
        assertThat(createdCustomer.getCategory()).isEqualTo(productDto.category());
    }
}
