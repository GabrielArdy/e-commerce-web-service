package com.gateway.desktop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.desktop.DataObject.DataObject;
import com.gateway.desktop.DataObject.Product;
import com.gateway.desktop.DataObject.Store;

@RestController
@RequestMapping("/api/desktop")
public class Desktop {

  private static final String API_URL = "http://35.221.163.216:9000/api";

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

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

  @GetMapping("/products/{id}")
  public ResponseEntity<Object> getProductById(@PathVariable("id") String id) {
    try {
      String response = restTemplate.getForObject(API_URL + "/products/" + id, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/products")
  public ResponseEntity<Object> createProduct(Product product) {
    try {
      String response = restTemplate.postForObject(API_URL + "/products", product, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, Product product) {
    try {
      restTemplate.put(API_URL + "/products/" + id, product);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Object> deleteProduct(@PathVariable("id") String id) {
    try {
      restTemplate.delete(API_URL + "/products/" + id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  // Store API

  @GetMapping("/stores/{id}")
  public ResponseEntity<Object> getStoreById(@PathVariable("id") String id) {
    try {
      String response = restTemplate.getForObject(API_URL + "/stores/" + id, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/stores")
  public ResponseEntity<Object> createStore(Store store) {
    try {
      String response = restTemplate.postForObject(API_URL + "/stores", store, String.class);
      DataObject dataObject = objectMapper.readValue(response, DataObject.class);
      return ResponseEntity.ok(dataObject);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/stores/{id}")
  public ResponseEntity<Object> updateStore(@PathVariable("id") String id, Store store) {
    try {
      restTemplate.put(API_URL + "/stores/" + id, store);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/stores/{id}")
  public ResponseEntity<Object> deleteStore(@PathVariable("id") String id) {
    try {
      restTemplate.delete(API_URL + "/stores/" + id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
