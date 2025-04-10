package com.stockcompare.repository;

import com.stockcompare.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for UserProfile entity.
 * This is part of implementing SOA by providing data access for user-related operations.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    
    /**
     * Find a user profile by username.
     * 
     * @param username The username to search for
     * @return Optional containing the user profile if found, empty otherwise
     */
    Optional<UserProfile> findByUsername(String username);
    
    /**
     * Find a user profile by email.
     * 
     * @param email The email to search for
     * @return Optional containing the user profile if found, empty otherwise
     */
    Optional<UserProfile> findByEmail(String email);
    
    /**
     * Check if a username already exists.
     * 
     * @param username The username to check
     * @return true if the username exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if an email already exists.
     * 
     * @param email The email to check
     * @return true if the email exists, false otherwise
     */
    boolean existsByEmail(String email);
} 