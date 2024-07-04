package com.gateway.desktop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.desktop.DataObject.DataObject;

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

}
