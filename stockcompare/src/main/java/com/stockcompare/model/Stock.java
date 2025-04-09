package com.stockcompare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Stock {
    
    @Id
    private String symbol;
    private String name;
    private String exchange;
    
    @OneToMany(mappedBy = "stock")
    private List<StockPrice> prices = new ArrayList<>();
    
    public Stock() {
    }
    
    public Stock(String symbol, String name, String exchange) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
    }
    
    public Stock(String symbol, String name, String exchange, List<StockPrice> prices) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
        this.prices = prices;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getExchange() {
        return exchange;
    }
    
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    
    public List<StockPrice> getPrices() {
        return prices;
    }
    
    public void setPrices(List<StockPrice> prices) {
        this.prices = prices;
    }
} 