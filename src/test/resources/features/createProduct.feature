Feature: Product Service

  Scenario: Create a new product
    Given a product payload with name "Pastel", price 15.0 and category "LANCHE"
    When the client requests to create a product
    Then the response should contain the product's ID and details