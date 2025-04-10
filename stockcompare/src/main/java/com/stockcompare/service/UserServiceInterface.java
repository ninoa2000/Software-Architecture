package com.stockcompare.service;

import com.stockcompare.model.UserProfile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service interface for user-related operations.
 * This follows SOA principles by providing a clear contract for user operations.
 */
public interface UserServiceInterface {
    
    /**
     * Create a new user profile.
     * 
     * @param username The username for the new user
     * @param email The email for the new user
     * @param fullName The full name of the new user (optional)
     * @return The created user profile
     * @throws IllegalArgumentException if the username or email already exists
     */
    UserProfile createUser(String username, String email, String fullName);
    
    /**
     * Get a user profile by username.
     * 
     * @param username The username to search for
     * @return Optional containing the user profile if found, empty otherwise
     */
    Optional<UserProfile> getUserByUsername(String username);
    
    /**
     * Get a user profile by email.
     * 
     * @param email The email to search for
     * @return Optional containing the user profile if found, empty otherwise
     */
    Optional<UserProfile> getUserByEmail(String email);
    
    /**
     * Get a user profile by ID.
     * 
     * @param id The user ID to search for
     * @return Optional containing the user profile if found, empty otherwise
     */
    Optional<UserProfile> getUserById(Long id);
    
    /**
     * Get all user profiles.
     * 
     * @return List of all user profiles
     */
    List<UserProfile> getAllUsers();
    
    /**
     * Update a user's profile information.
     * 
     * @param userId The ID of the user to update
     * @param email The new email (optional, pass null to keep existing)
     * @param fullName The new full name (optional, pass null to keep existing)
     * @param defaultDateRangeDays The new default date range (optional, pass null to keep existing)
     * @return The updated user profile
     * @throws IllegalArgumentException if the user doesn't exist or the email is already in use
     */
    UserProfile updateUser(Long userId, String email, String fullName, Integer defaultDateRangeDays);
    
    /**
     * Delete a user profile.
     * 
     * @param userId The ID of the user to delete
     * @return true if the user was deleted, false if the user doesn't exist
     */
    boolean deleteUser(Long userId);
    
    /**
     * Add a stock to a user's favorites.
     * 
     * @param userId The ID of the user
     * @param stockSymbol The stock symbol to add to favorites
     * @return The updated user profile
     * @throws IllegalArgumentException if the user doesn't exist
     */
    UserProfile addFavoriteStock(Long userId, String stockSymbol);
    
    /**
     * Remove a stock from a user's favorites.
     * 
     * @param userId The ID of the user
     * @param stockSymbol The stock symbol to remove from favorites
     * @return The updated user profile
     * @throws IllegalArgumentException if the user doesn't exist
     */
    UserProfile removeFavoriteStock(Long userId, String stockSymbol);
    
    /**
     * Get a user's favorite stocks.
     * 
     * @param userId The ID of the user
     * @return Set of stock symbols that the user has favorited
     * @throws IllegalArgumentException if the user doesn't exist
     */
    Set<String> getFavoriteStocks(Long userId);
} 