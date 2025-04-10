package com.stockcompare.service;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;
import com.stockcompare.repository.StockPriceRepository;
import com.stockcompare.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Implementation of the StockServiceInterface.
 * This service handles core stock-related operations.
 */
@Service
public class StockService implements StockServiceInterface {

    private static final Logger logger = Logger.getLogger(StockService.class.getName());

    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;
    private final ExternalStockDataServiceInterface externalStockDataService;

    public StockService(StockRepository stockRepository, StockPriceRepository stockPriceRepository, 
                        AlphaVantageService alphaVantageService) {
        this.stockRepository = stockRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.externalStockDataService = alphaVantageService;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<Stock> findStock(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    @Override
    @Transactional
    public Stock getOrCreateStock(String symbol) {
        return stockRepository.findBySymbol(symbol)
                .orElseGet(() -> {
                    Stock externalStock = externalStockDataService.getStock(symbol);
                    if (externalStock != null) {
                        return stockRepository.save(externalStock);
                    }
                    throw new IllegalArgumentException("Invalid stock symbol: " + symbol);
                });
    }

    @Override
    @Transactional
    public List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate) {
        // Validate date range (max 2 years)
        if (ChronoUnit.DAYS.between(startDate, endDate) > 730) {
            throw new IllegalArgumentException("Date range cannot exceed 2 years");
        }

        try {
            Stock stock = getOrCreateStock(symbol);
            
            // Check if we have the data in the database
            List<StockPrice> prices = stockPriceRepository
                    .findByStockAndDateBetweenOrderByDate(stock, startDate, endDate);
            
            logger.info("Found " + prices.size() + " price records in database for " + symbol);
            
            // If we don't have data or it's incomplete, fetch from external service
            if (prices.isEmpty() || prices.size() < ChronoUnit.DAYS.between(startDate, endDate) / 2) {
                logger.info("Fetching stock price data from external service for " + symbol);
                List<StockPrice> externalStockPrices = externalStockDataService.getHistoricalPrices(stock, startDate, endDate);
                
                if (externalStockPrices.isEmpty()) {
                    logger.warning("No price data returned from external service for " + symbol);
                    
                    // Return whatever we have in the database, even if incomplete
                    if (!prices.isEmpty()) {
                        logger.info("Returning " + prices.size() + " price records from database for " + symbol);
                        return prices;
                    }
                    
                    throw new IllegalArgumentException("Could not retrieve price data for " + symbol + ". Please verify the stock symbol is correct.");
                }
                
                // Save to database
                int savedCount = 0;
                for (StockPrice price : externalStockPrices) {
                    if (!stockPriceRepository.existsByStockAndDate(stock, price.getDate())) {
                        stockPriceRepository.save(price);
                        savedCount++;
                    }
                }
                
                logger.info("Saved " + savedCount + " new price records for " + symbol);
                
                // Get updated prices from database
                prices = stockPriceRepository.findByStockAndDateBetweenOrderByDate(stock, startDate, endDate);
            }
            
            return prices;
        } catch (IllegalArgumentException e) {
            // Rethrow these as they are already proper validation errors
            throw e;
        } catch (Exception e) {
            logger.severe("Error retrieving stock prices for " + symbol + ": " + e.getMessage());
            throw new IllegalArgumentException("Failed to retrieve data for " + symbol + ": " + e.getMessage());
        }
    }
} 