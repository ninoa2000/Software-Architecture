package com.stockcompare;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;
import com.stockcompare.repository.StockPriceRepository;
import com.stockcompare.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@SpringBootApplication
public class StockCompareApplication {
    
    private static final Logger logger = Logger.getLogger(StockCompareApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(StockCompareApplication.class, args);
    }
    
    @Bean
    @Transactional
    public CommandLineRunner initSampleData(StockRepository stockRepository, StockPriceRepository stockPriceRepository) {
        return args -> {
            logger.info("Checking for existing stock data...");
            
            // Clear any existing data for fresh start
            stockPriceRepository.deleteAll();
            stockRepository.deleteAll();
            
            logger.info("Initializing sample stock data...");
            
            // Create sample stocks
            List<Stock> stocks = new ArrayList<>();
            stocks.add(new Stock("AAPL", "Apple Inc.", "NASDAQ"));
            stocks.add(new Stock("MSFT", "Microsoft Corporation", "NASDAQ"));
            stocks.add(new Stock("GOOGL", "Alphabet Inc.", "NASDAQ"));
            stocks.add(new Stock("AMZN", "Amazon.com Inc.", "NASDAQ"));
            stocks.add(new Stock("TSLA", "Tesla, Inc.", "NASDAQ"));
            
            // Save stocks
            for (Stock stock : stocks) {
                stockRepository.save(stock);
            }
            logger.info("Saved " + stocks.size() + " sample stocks");
            
            // Generate sample price data for the past 30 days
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(30);
            
            Random random = new Random(42); // Use seed for reproducible data
            
            for (Stock stock : stocks) {
                // Starting prices for each stock
                double startingPrice;
                switch (stock.getSymbol()) {
                    case "AAPL": startingPrice = 170.0; break;
                    case "MSFT": startingPrice = 330.0; break;
                    case "GOOGL": startingPrice = 140.0; break;
                    case "AMZN": startingPrice = 180.0; break;
                    case "TSLA": startingPrice = 175.0; break;
                    default: startingPrice = 100.0;
                }
                
                double currentPrice = startingPrice;
                int priceCount = 0;
                
                // Generate prices for each day
                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    // Skip weekends
                    if (date.getDayOfWeek().getValue() > 5) {
                        continue;
                    }
                    
                    // Simulate daily price movement (within +/- 3%)
                    double dailyChange = (random.nextDouble() * 6 - 3) / 100.0;
                    currentPrice = currentPrice * (1 + dailyChange);
                    
                    // Calculate high and low based on close price
                    double dailyVolatility = random.nextDouble() * 0.02; // 0-2% volatility
                    double high = currentPrice * (1 + dailyVolatility);
                    double low = currentPrice * (1 - dailyVolatility);
                    
                    // Opening price is usually close to previous close
                    double open = currentPrice * (1 + (random.nextDouble() * 0.02 - 0.01));
                    
                    // Volume varies between 5-15 million shares
                    long volume = 5_000_000 + (long)(random.nextDouble() * 10_000_000);
                    
                    // Create and save the price entry
                    StockPrice stockPrice = new StockPrice(
                            stock, 
                            date, 
                            round(open), 
                            round(high), 
                            round(low), 
                            round(currentPrice), 
                            volume
                    );
                    
                    stockPriceRepository.save(stockPrice);
                    priceCount++;
                }
                
                logger.info("Generated " + priceCount + " price data points for " + stock.getSymbol());
            }
            
            logger.info("Sample data initialization complete");
        };
    }
    
    // Helper method to round to 2 decimal places
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
} 