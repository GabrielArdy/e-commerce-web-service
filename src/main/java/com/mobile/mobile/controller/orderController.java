package com.mobile.mobile.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.mobile.mobile.model.cart;
import com.mobile.mobile.model.order;
import com.mobile.mobile.model.cartItem;
import com.mobile.mobile.repository.cartRepository;
import com.mobile.mobile.repository.orderRepository;

@Controller
@RequestMapping("/order")
public class orderController {

    @Autowired
    private cartRepository cartRepository;

    @Autowired
    private orderRepository orderRepository;

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> viewOrder(@PathVariable String orderId) {
        Optional<order> orderOptional = orderRepository.findById(orderId);

        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        order order = orderOptional.get();
        return ResponseEntity.ok(order);
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<Object> createOrder(@RequestBody order newOrder) {
        Optional<cart> cartOptional = cartRepository.findById(newOrder.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cart cart = cartOptional.get();
        if (!cart.getStatus().equals("Waiting for payment")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error, Cart status : " + cart.getStatus());
        }

        // Set order details
        newOrder.setOrderId(UUID.randomUUID().toString());
        newOrder.setStatus("paid");
        newOrder.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        newOrder.setPaymentMethod("Credit Card");

        // Set userId from cart
        newOrder.setUserId(cart.getUserId());
        newOrder.setProductList(cart.getproductList());
        cart.setStatus("paid");
        cartRepository.save(cart);

        order savedOrder = orderRepository.save(newOrder);

        Map<String, Object> response = new HashMap<>();

        response.put("orderId", savedOrder.getOrderId());
        response.put("message", "Order created");
        response.put("status", "success");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
