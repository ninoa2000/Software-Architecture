package com.stockcompare.service;

import com.stockcompare.model.StockPrice;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Service interface for stock comparison operations.
 * Following SOA principles, this interface separates comparison functionality
 * from basic stock operations.
 */
public interface ComparisonServiceInterface {
    
    /**
     * Compares two stocks over a given time period.
     * 
     * @param symbol1 First stock symbol
     * @param symbol2 Second stock symbol
     * @param from Start date for comparison
     * @param to End date for comparison
     * @return Map containing the stock prices for both symbols
     * @throws IllegalArgumentException if either symbol is invalid or the date range is invalid
     */
    Map<String, List<StockPrice>> compareStocks(
            String symbol1, 
            String symbol2, 
            LocalDate from, 
            LocalDate to);
    
    /**
     * Calculates the performance metrics for a stock over a given time period.
     * 
     * @param symbol Stock symbol
     * @param from Start date
     * @param to End date
     * @return Map containing performance metrics like percent change, volatility, etc.
     * @throws IllegalArgumentException if the symbol is invalid or the date range is invalid
     */
    Map<String, Double> calculatePerformance(
            String symbol,
            LocalDate from,
            LocalDate to);
    
    /**
     * Compares the performance metrics of multiple stocks.
     * 
     * @param symbols List of stock symbols to compare
     * @param from Start date
     * @param to End date
     * @return Map of symbol to performance metrics
     * @throws IllegalArgumentException if any symbol is invalid or the date range is invalid
     */
    Map<String, Map<String, Double>> comparePerformance(
            List<String> symbols,
            LocalDate from,
            LocalDate to);
} 