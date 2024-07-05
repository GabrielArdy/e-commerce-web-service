package com.gateway.desktop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.desktop.DataObject.DataObject;

@RestController
@RequestMapping("/api/web")
public class Web {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String API_URL = "http://35.221.163.216:9000/api";

  @GetMapping("/products")
  public ResponseEntity<Object> getAllProduct() {
    try {
      String response = restTemplate.getForObject(API_URL + "/products", String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  // Cart
  @PostMapping("/cart/{userId}")
  public ResponseEntity<Object> createCart(String userId) {
    try {
      String response = restTemplate.getForObject(API_URL + "/cart/" + userId, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/cart/{userId}")
  public ResponseEntity<Object> getCartByUserId(String userId) {
    try {
      String response = restTemplate.getForObject(API_URL + "/cart/" + userId, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/cart/{UserId}/products/{ProductId}/{Quantity}")
  public ResponseEntity<Object> addProductToCart(String userId, String productId, int quantity) {
    try {
      String response = restTemplate
          .getForObject(API_URL + "/cart/" + userId + "/products/" + productId + "/" + quantity, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/cart/{UserId}/products/{ProductId}")
  public ResponseEntity<Object> removeProductFromCart(String userId, String productId) {
    try {
      restTemplate.delete(API_URL + "/cart/" + userId + "/products/" + productId);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/cart/{UserId}/checkout")
  public ResponseEntity<Object> checkoutCart(String userId) {
    try {
      String response = restTemplate.getForObject(API_URL + "/cart/" + userId + "/checkout2", String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/transaction/{transactionId}/paid/{paymentMethod}")
  public ResponseEntity<Object> paidTransaction(String transactionId, String paymentMethod) {
    try {
      String response = restTemplate
          .getForObject(API_URL + "/transaction/" + transactionId + "/paid/" + paymentMethod, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/transaction/{transactionId}")
  public ResponseEntity<Object> getTransactionById(String transactionId) {
    try {
      String response = restTemplate.getForObject(API_URL + "/transaction/" + transactionId, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  // Shipping
  @GetMapping("/shipping/{Receipt}")
  public ResponseEntity<Object> getShippingByReceipt(String receipt) {
    try {
      String response = restTemplate.getForObject(API_URL + "/shipping/" + receipt, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
