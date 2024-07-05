package com.gateway.desktop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.desktop.DataObject.DataObject;
import com.gateway.desktop.DataObject.ResponseObject;

@RestController
@RequestMapping("/api/mobile")
public class Mobile {

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
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

  // Cart
  @PostMapping("/cart/{userId}")
  public ResponseEntity<Object> createCart(@PathVariable("userId") String userId) {
    try {
      String response = restTemplate.postForObject(API_URL + "/cart/" + userId, null,
          String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400,
          e.getMessage()));
    }
  }

  @GetMapping("/cart/{userId}")
  public ResponseEntity<Object> getCartByUserId(@PathVariable("userId") String userId) {
    try {
      String response = restTemplate.getForObject(API_URL + "/cart/" + userId, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

  @PostMapping("/cart/{UserId}/products/{ProductId}/{Quantity}")
  public ResponseEntity<Object> addProductToCart(@PathVariable("UserId") String userId,
      @PathVariable("ProductId") String productId, @PathVariable("Quantity") int quantity) {
    try {
      String response = restTemplate
          .postForObject(API_URL + "/cart/" + userId + "/products/" + productId + "/" + quantity, null, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

  @DeleteMapping("/cart/{UserId}/products/{ProductId}")
  public ResponseEntity<Object> removeProductFromCart(@PathVariable("UserId") String userId,
      @PathVariable("ProductId") String productId) {
    try {
      restTemplate.delete(API_URL + "/cart/" + userId + "/products/" + productId);
      return ResponseEntity.ok(new ResponseObject(200, "Product Removed Successfuly"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

  @PostMapping("/cart/{UserId}/checkout")
  public ResponseEntity<Object> checkoutCart(@PathVariable("UserId") String userId) {
    try {
      String response = restTemplate.postForObject(API_URL + "/cart/" + userId + "/checkout2", null, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

  @PostMapping("/transaction/{transactionId}/paid/{paymentMethod}")
  public ResponseEntity<Object> paidTransaction(@PathVariable("transactionId") String transactionId,
      @PathVariable("paymentMethod") String paymentMethod) {
    try {
      String response = restTemplate
          .postForObject(API_URL + "/transaction/" + transactionId + "/paid/" + paymentMethod, null, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

  @GetMapping("/transaction/{transactionId}")
  public ResponseEntity<Object> getTransactionById(@PathVariable("transactionId") String transactionId) {
    try {
      String response = restTemplate.getForObject(API_URL + "/transaction/" + transactionId, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

  // Shipping
  @GetMapping("/shipping/{shippingId}")
  public ResponseEntity<Object> getShippingByReceipt(@PathVariable("shippingId") String shippingId) {
    try {
      String response = restTemplate.getForObject(API_URL + "/shipping/" + shippingId, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

}
