package com.mobile.mobile.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.mobile.mobile.model.cart;
import com.mobile.mobile.model.cartItem;
import com.mobile.mobile.model.product;
import com.mobile.mobile.model.productRequest.AddProductRequest;
import com.mobile.mobile.model.productRequest.CartRequest;
import com.mobile.mobile.model.productRequest.DeleteProductRequest;
import com.mobile.mobile.repository.cartRepository;
import com.mobile.mobile.repository.productRepository;
import com.mobile.mobile.service.cartService;

@Controller
@RequestMapping(path = "/cart")
public class cartController {
    @Autowired
    private cartRepository cartRepository;

    @Autowired
    private productRepository productRepository;

    @Autowired
    private cartService cartService;

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<String> createCart(@RequestBody cart newCart) {
        if (newCart.getId() == null || newCart.getId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart ID cannot be empty");
        }

        if (cartRepository.existsById(newCart.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cart with this ID already exists");
        }

        newCart.setStatus("pending"); // Set initial status to pending
        cartRepository.insert(newCart);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cart created successfully");
    }

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<String> addProductToCart(@RequestBody AddProductRequest request) {
        Optional<cart> cartOptional = cartRepository.findById(request.getCartId());
        Optional<product> productOptional = productRepository.findById(request.getProductId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        cart cart = cartOptional.get();

        if ("paid".equals(cart.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot add product to a paid cart");
        }

        List<cartItem> productList = cart.getproductList();
        if (productList == null) {
            productList = new ArrayList<>();
            cart.setproductList(productList);
        }

        String productId = request.getProductId();
        boolean productExists = false;
        for (cartItem item : productList) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + request.getJumlah());
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            cartItem newItem = new cartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(request.getJumlah());
            productList.add(newItem);
        }

        cartRepository.save(cart);
        return ResponseEntity.ok("Product added to cart");
    }

    @PostMapping("/view")
    public @ResponseBody ResponseEntity<cart> viewCart(@RequestBody CartRequest request) {
        Optional<cart> cartOptional = cartRepository.findById(request.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(cartOptional.get());
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteProductFromCart(@RequestBody DeleteProductRequest request) {
        Optional<cart> cartOptional = cartRepository.findById(request.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cart cart = cartOptional.get();
        List<cartItem> productList = cart.getproductList();
        if (productList != null) {
            productList.removeIf(item -> item.getProductId().equals(request.getProductId()));
            cart.setproductList(productList);
            cartRepository.save(cart);
        }

        return ResponseEntity.ok("Product removed from cart");
    }

    @PostMapping("/checkout")
    public @ResponseBody ResponseEntity<Object> checkout(@RequestBody CartRequest request) {
        Optional<cart> cartOptional = cartRepository.findById(request.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cart cart = cartOptional.get();

        if (cart.getproductList() == null || cart.getproductList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No product added, Cart is empty");
        }
        if (!cart.getStatus().equals("pending")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart is " + cart.getStatus());
        }

        double total = cartService.getTotalPrice(cart);

        cart.setStatus("Waiting for payment");
        cart.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        cartRepository.save(cart);

        // Prepare response JSON
        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("message", "Checkout completed. Order created");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

}