package com.gateway.desktop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.desktop.DataObject.ResponseObject;

@RestController
@RequestMapping("/api/website")
public class Website {

  @Autowired
  private RestTemplate restTemplate;

  private static final String API_URL = "http://35.221.163.216:9000/api";

  @PutMapping("/shipping/{shippingId}/status/{status}")
  public ResponseEntity<Object> updateShippingStatus(String shippingId, String status) {
    try {
      restTemplate.put(API_URL + "/shipping/" + shippingId + "/status/" + status, null);
      return ResponseEntity.ok(new ResponseObject(200, "Product Updated Successfuly"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject(400, e.getMessage()));
    }
  }

}
