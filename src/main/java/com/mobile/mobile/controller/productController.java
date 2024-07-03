package com.mobile.mobile.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.mobile.mobile.model.product;
import com.mobile.mobile.repository.productRepository;

@Controller
@RequestMapping(path = "/product")
public class productController {
    @Autowired
    private productRepository productRepository;

    @GetMapping("/getall")
    public @ResponseBody Iterable<product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/getbyname")
    public @ResponseBody List<product> getProductsByName(@RequestParam String name) {
        return productRepository.findByNameRegex(name);
    }

    @PostMapping("/search")
    public @ResponseBody List<product> searchProducts(@RequestParam String name, @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        return productRepository.findByNameRegexAndPriceBetween(name, minPrice, maxPrice);
    }

    @PostMapping("/add")
    public @ResponseBody String addProduct(@RequestBody product product) {
        productRepository.insert(product);
        return "Product added successfully";
    }

    @PostMapping("/update")
    public @ResponseBody ResponseEntity<String> updateProduct(@RequestBody product product) {
        if (!productRepository.existsById(product.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productRepository.save(product);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteProduct(@RequestParam String id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
