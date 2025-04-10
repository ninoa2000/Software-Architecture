package com.stockcompare.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class StockPrice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "stock_symbol")
    private Stock stock;
    
    private LocalDate date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    
    public StockPrice() {
    }
    
    public StockPrice(Long id, Stock stock, LocalDate date, double open, double high, double low, double close, long volume) {
        this.id = id;
        this.stock = stock;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }
    
    public StockPrice(Stock stock, LocalDate date, double open, double high, double low, double close, long volume) {
        this.stock = stock;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Stock getStock() {
        return stock;
    }
    
    public void setStock(Stock stock) {
        this.stock = stock;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public double getOpen() {
        return open;
    }
    
    public void setOpen(double open) {
        this.open = open;
    }
    
    public double getHigh() {
        return high;
    }
    
    public void setHigh(double high) {
        this.high = high;
    }
    
    public double getLow() {
        return low;
    }
    
    public void setLow(double low) {
        this.low = low;
    }
    
    public double getClose() {
        return close;
    }
    
    public void setClose(double close) {
        this.close = close;
    }
    
    public long getVolume() {
        return volume;
    }
    
    public void setVolume(long volume) {
        this.volume = volume;
    }
} 