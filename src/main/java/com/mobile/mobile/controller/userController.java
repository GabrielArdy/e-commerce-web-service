package com.mobile.mobile.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.mobile.model.user;
import com.mobile.mobile.repository.userRepository;

@Controller
@RequestMapping(path = "/user")
public class userController {
    @Autowired
    private userRepository ur;

    @GetMapping("/getall")
    public @ResponseBody Iterable<user> getUser() {
        return ur.findAll();
    }

    @PostMapping("/signin")
    public @ResponseBody String addUser(@RequestBody user u) {
        // Check if a user with the same id, username, or email already exists
        if (ur.existsById(u.getId())) {
            return "id exists";
        }
        // Insert the new user
        ur.insert(u);
        return "User added successfully";
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<String> loginUser(@RequestBody user u) {
        user existingUser = ur.findByUsername(u.getUsername());
        if (existingUser == null || !existingUser.getPassword().equals(u.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        return ResponseEntity.ok("Login successful");
    }

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }

        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        user existingUser = ur.findByUsername(username);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!existingUser.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }

        ur.deleteByUsername(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/changepass")
    public @ResponseBody ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("password");

        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }

        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        user existingUser = ur.findByUsername(username);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        existingUser.setPassword(newPassword);
        ur.save(existingUser);
        return ResponseEntity.ok("Password changed successfully");
    }

}
