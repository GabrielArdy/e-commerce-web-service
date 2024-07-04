package com.mobile.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobile.mobile.model.cart;
import com.mobile.mobile.model.cartItem;
import com.mobile.mobile.model.product;
import com.mobile.mobile.repository.productRepository;

@Service
public class cartService {

    @Autowired
    private productRepository productRepository;

    // Calculate total price based on cart items
    public double getTotalPrice(cart cart) {
        double total = 0.0;
        List<cartItem> productList = cart.getproductList();
        if (productList != null) {
            for (cartItem item : productList) {
                total += item.getQuantity() * getProductPrice(item.getProductId());
            }
        }
        return total;
    }

    // Fetch product price from repository
    private double getProductPrice(String productId) {
        product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            return product.getPrice();
        } else {
            throw new RuntimeException("Product not found for id: " + productId);
        }
    }
}
