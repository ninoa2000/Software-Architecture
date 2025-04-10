package com.stockcompare.controller;

import com.stockcompare.model.UserProfile;
import com.stockcompare.service.UserServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * REST controller for user-related operations.
 * This follows SOA principles by exposing user service functionality through REST endpoints.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String fullName) {
        
        try {
            logger.info("Creating user: " + username);
            UserProfile userProfile = userService.createUser(username, email, fullName);
            return ResponseEntity.status(HttpStatus.CREATED).body(userProfile);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to create user: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        logger.info("Getting all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        logger.info("Getting user by id: " + id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "User not found with id: " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                });
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        logger.info("Getting user by username: " + username);
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "User not found with username: " + username);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) Integer defaultDateRangeDays) {
        
        try {
            logger.info("Updating user with id: " + id);
            UserProfile updatedUser = userService.updateUser(id, email, fullName, defaultDateRangeDays);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to update user: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with id: " + id);
        boolean deleted = userService.deleteUser(id);
        
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/{id}/favorites/{symbol}")
    public ResponseEntity<?> addFavoriteStock(
            @PathVariable Long id,
            @PathVariable String symbol) {
        
        try {
            logger.info("Adding stock " + symbol + " to favorites for user id: " + id);
            UserProfile updatedUser = userService.addFavoriteStock(id, symbol);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to add favorite stock: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}/favorites/{symbol}")
    public ResponseEntity<?> removeFavoriteStock(
            @PathVariable Long id,
            @PathVariable String symbol) {
        
        try {
            logger.info("Removing stock " + symbol + " from favorites for user id: " + id);
            UserProfile updatedUser = userService.removeFavoriteStock(id, symbol);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to remove favorite stock: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/{id}/favorites")
    public ResponseEntity<?> getFavoriteStocks(@PathVariable Long id) {
        try {
            logger.info("Getting favorite stocks for user id: " + id);
            Set<String> favorites = userService.getFavoriteStocks(id);
            return ResponseEntity.ok(favorites);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to get favorite stocks: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
} 