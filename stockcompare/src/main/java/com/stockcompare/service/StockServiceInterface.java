package com.stockcompare.service;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for stock-related operations.
 * This follows SOA principles by providing a clear contract for stock operations.
 */
public interface StockServiceInterface {
    
    /**
     * Retrieves all stocks in the system.
     * 
     * @return List of all stocks
     */
    List<Stock> getAllStocks();
    
    /**
     * Finds a stock by its symbol.
     * 
     * @param symbol The stock symbol to search for
     * @return Optional containing the stock if found, empty otherwise
     */
    Optional<Stock> findStock(String symbol);
    
    /**
     * Retrieves an existing stock or creates a new one if it doesn't exist.
     * 
     * @param symbol The stock symbol
     * @return The retrieved or created stock
     * @throws IllegalArgumentException if the symbol is invalid
     */
    Stock getOrCreateStock(String symbol);
    
    /**
     * Retrieves stock prices for a given symbol and date range.
     * 
     * @param symbol The stock symbol
     * @param startDate The start date for the price range
     * @param endDate The end date for the price range
     * @return List of stock prices within the date range
     * @throws IllegalArgumentException if the date range is invalid or the symbol cannot be found
     */
    List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate);
} 