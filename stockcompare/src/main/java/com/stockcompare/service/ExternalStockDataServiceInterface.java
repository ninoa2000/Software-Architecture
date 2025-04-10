package com.stockcompare.service;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for external stock data services.
 * Following SOA principles, this interface defines a contract for any external
 * stock data provider, allowing for easy swapping of implementations.
 */
public interface ExternalStockDataServiceInterface {
    
    /**
     * Retrieves basic stock information from an external API.
     * 
     * @param symbol The stock symbol to lookup
     * @return Stock object with the retrieved information, or a basic stock if information is unavailable
     */
    Stock getStock(String symbol);
    
    /**
     * Retrieves historical price data for a stock within a given date range.
     * 
     * @param stock The stock entity to retrieve prices for
     * @param startDate The start date for the historical data
     * @param endDate The end date for the historical data
     * @return List of stock prices within the date range
     */
    List<StockPrice> getHistoricalPrices(Stock stock, LocalDate startDate, LocalDate endDate);
} 