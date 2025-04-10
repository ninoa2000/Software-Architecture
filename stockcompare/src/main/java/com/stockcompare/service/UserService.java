package com.stockcompare.service;

import com.stockcompare.model.UserProfile;
import com.stockcompare.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Implementation of the UserServiceInterface.
 * This service handles user-related operations.
 */
@Service
public class UserService implements UserServiceInterface {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserProfileRepository userProfileRepository;

    public UserService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional
    public UserProfile createUser(String username, String email, String fullName) {
        // Validate inputs
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        // Check if username or email already exists
        if (userProfileRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        if (userProfileRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        // Create and save the user profile
        UserProfile userProfile = new UserProfile(username, email, fullName);
        userProfile.setLastLogin(LocalDateTime.now());
        
        logger.info("Creating new user: " + username);
        return userProfileRepository.save(userProfile);
    }

    @Override
    public Optional<UserProfile> getUserByUsername(String username) {
        return userProfileRepository.findByUsername(username);
    }

    @Override
    public Optional<UserProfile> getUserByEmail(String email) {
        return userProfileRepository.findByEmail(email);
    }

    @Override
    public Optional<UserProfile> getUserById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    @Transactional
    public UserProfile updateUser(Long userId, String email, String fullName, Integer defaultDateRangeDays) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        // Update email if provided and not already in use by another user
        if (email != null && !email.trim().isEmpty()) {
            Optional<UserProfile> existingUserWithEmail = userProfileRepository.findByEmail(email);
            if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(userId)) {
                throw new IllegalArgumentException("Email already in use by another user: " + email);
            }
            userProfile.setEmail(email);
        }
        
        // Update full name if provided
        if (fullName != null) {
            userProfile.setFullName(fullName);
        }
        
        // Update default date range if provided
        if (defaultDateRangeDays != null) {
            if (defaultDateRangeDays < 1 || defaultDateRangeDays > 365 * 5) {
                throw new IllegalArgumentException("Default date range must be between 1 and 1825 days");
            }
            userProfile.setDefaultDateRangeDays(defaultDateRangeDays);
        }
        
        logger.info("Updating user: " + userProfile.getUsername());
        return userProfileRepository.save(userProfile);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        if (userProfileRepository.existsById(userId)) {
            logger.info("Deleting user with id: " + userId);
            userProfileRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public UserProfile addFavoriteStock(Long userId, String stockSymbol) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        userProfile.addFavoriteStock(stockSymbol);
        logger.info("Adding stock " + stockSymbol + " to favorites for user: " + userProfile.getUsername());
        return userProfileRepository.save(userProfile);
    }

    @Override
    @Transactional
    public UserProfile removeFavoriteStock(Long userId, String stockSymbol) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        userProfile.removeFavoriteStock(stockSymbol);
        logger.info("Removing stock " + stockSymbol + " from favorites for user: " + userProfile.getUsername());
        return userProfileRepository.save(userProfile);
    }

    @Override
    public Set<String> getFavoriteStocks(Long userId) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        // Return a copy to prevent modifications to the user's set
        return new HashSet<>(userProfile.getFavoriteStocks());
    }
} 