package com.stockcompare.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a user profile in the system.
 * This is part of implementing SOA principles by separating user-related functionality.
 */
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "full_name")
    private String fullName;
    
    @ElementCollection
    @CollectionTable(name = "favorite_stocks", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "stock_symbol")
    private Set<String> favoriteStocks = new HashSet<>();
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "default_date_range_days")
    private Integer defaultDateRangeDays = 30; // Default to 30 days
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Default constructor
    public UserProfile() {
    }
    
    // Constructor with required fields
    public UserProfile(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    // Constructor with all fields
    public UserProfile(String username, String email, String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Set<String> getFavoriteStocks() {
        return favoriteStocks;
    }
    
    public void setFavoriteStocks(Set<String> favoriteStocks) {
        this.favoriteStocks = favoriteStocks;
    }
    
    public void addFavoriteStock(String stockSymbol) {
        this.favoriteStocks.add(stockSymbol);
    }
    
    public void removeFavoriteStock(String stockSymbol) {
        this.favoriteStocks.remove(stockSymbol);
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public Integer getDefaultDateRangeDays() {
        return defaultDateRangeDays;
    }
    
    public void setDefaultDateRangeDays(Integer defaultDateRangeDays) {
        this.defaultDateRangeDays = defaultDateRangeDays;
    }
    
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", favoriteStocks=" + favoriteStocks +
                ", defaultDateRangeDays=" + defaultDateRangeDays +
                '}';
    }
} 