package com.mobile.mobile.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.mobile.model.cart;
import com.mobile.mobile.model.order;
import com.mobile.mobile.repository.cartRepository;
import com.mobile.mobile.repository.orderRepository;

@Controller
@RequestMapping("/order")
public class orderController {

    @Autowired
    private cartRepository cartRepository;

    @Autowired
    private orderRepository orderRepository;

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<String> createOrder(@RequestBody order newOrder) {
        Optional<cart> cartOptional = cartRepository.findById(newOrder.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cart cart = cartOptional.get();

        // Set order details
        newOrder.setOrderId(UUID.randomUUID().toString());
        newOrder.setStatus("paid");
        newOrder.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // Set payment method from request
        // Assuming paymentMethod is provided in the newOrder request body
        cart.setStatus("paid");
        cartRepository.save(cart);

        // Save the new order
        orderRepository.save(newOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Order created successfully. Order ID: " + newOrder.getOrderId());
    }
}