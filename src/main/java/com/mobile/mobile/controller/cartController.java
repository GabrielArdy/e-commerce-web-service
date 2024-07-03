package com.mobile.mobile.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.mobile.model.cart;
import com.mobile.mobile.model.cartItem;
import com.mobile.mobile.model.order;
import com.mobile.mobile.model.product;
import com.mobile.mobile.model.productRequest.AddProductRequest;
import com.mobile.mobile.model.productRequest.CartRequest;
import com.mobile.mobile.model.productRequest.DeleteProductRequest;
import com.mobile.mobile.repository.cartRepository;
import com.mobile.mobile.repository.orderRepository;
import com.mobile.mobile.repository.productRepository;

@Controller
@RequestMapping(path = "/cart")
public class cartController {
    @Autowired
    private cartRepository cartRepository;

    @Autowired
    private productRepository productRepository;

    @Autowired
    private orderRepository orderRepository;

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<String> createCart(@RequestBody cart newCart) {
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
            return ResponseEntity.status(404).body("Cart not found");
        }
        if (!productOptional.isPresent()) {
            return ResponseEntity.status(404).body("Product not found");
        }

        cart cart = cartOptional.get();

        if ("paid".equals(cart.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot add product to a paid cart");
        }

        List<cartItem> listItem = cart.getItems();
        if (listItem == null) {
            listItem = new ArrayList<>(); // jika list List<cartItem> kosong dibuatkan
        }

        product product = productOptional.get();
        boolean productExists = false;
        for (int i = 0; i < listItem.size(); i++) { // isi listItem dicek satu satu
            cartItem item = listItem.get(i); // ubah variabel
            if (item.getProduct().getId().equals(product.getId())) {
                // jika item id sama dengan produk yang baru ditambahkan maka kuantitas akan
                // ditambahkan sesuai jumlah yang dimasukan
                item.setQuantity(item.getQuantity() + request.getJumlah());
                productExists = true; // skip if dibawah
                break;
            }
        }

        if (productExists == false) { // jika barang baru yang dimasukan belum ada
            cartItem cartItem = new cartItem(); // buat item baru
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getJumlah());
            listItem.add(cartItem); // masukan kedalam listItem
        }

        cart.setItems(listItem); // save ke cart listItems
        cartRepository.save(cart); // simpen ulang semua objek Sesuai model
        return ResponseEntity.ok("Product added to cart");
    }

    @GetMapping("/view")
    public @ResponseBody ResponseEntity<cart> viewCart(@RequestBody CartRequest request) {
        Optional<cart> cartOptional = cartRepository.findById(request.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok(cartOptional.get());
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteProductFromCart(@RequestBody DeleteProductRequest request) {
        Optional<cart> cartOptional = cartRepository.findById(request.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(404).body("Cart not found");
        }

        cart cart = cartOptional.get();
        List<cartItem> items = cart.getItems();
        if (items != null) {
            items.removeIf(item -> item.getProduct().getId().equals(request.getProductId()));
            cart.setItems(items);
            cartRepository.save(cart);
        }

        return ResponseEntity.ok("Product removed from cart");
    }

    @PostMapping("/checkout")
    public @ResponseBody ResponseEntity<String> checkout(@RequestBody CartRequest request) {
        Optional<cart> cartOptional = cartRepository.findById(request.getCartId());

        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(404).body("Cart not found");
        }

        cart cart = cartOptional.get();
        double total = cart.getTotalPrice();

        // Set cart status to "Waiting for payment" and add timestamp
        cart.setStatus("Waiting for payment");
        cart.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        cartRepository.save(cart);

        return ResponseEntity.ok("Checkout completed. Total amount: " + total);

        // // Create a new order
        // order newOrder = new order();
        // newOrder.setOrderId(UUID.randomUUID().toString());
        // newOrder.setStatus("paid");
        // newOrder.setPaymentMethod("Specify your payment method here");
        // newOrder.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
        // Date()));

        // // Save the new order
        // orderRepository.save(newOrder); // Correct method call for saving the order

        // return ResponseEntity.ok("Checkout completed. Total amount: " + total + ".
        // Order ID: " + newOrder.getOrderId());
    }
}
