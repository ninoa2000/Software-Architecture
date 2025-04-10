package com.stockcompare.controller;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;
import com.stockcompare.repository.StockPriceRepository;
import com.stockcompare.repository.StockRepository;
import com.stockcompare.service.ComparisonServiceInterface;
import com.stockcompare.service.StockServiceInterface;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private static final Logger logger = Logger.getLogger(StockController.class.getName());
    private final StockServiceInterface stockService;
    private final ComparisonServiceInterface comparisonService;
    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;
    
    public StockController(StockServiceInterface stockService, 
                          ComparisonServiceInterface comparisonService,
                          StockRepository stockRepository, 
                          StockPriceRepository stockPriceRepository) {
        this.stockService = stockService;
        this.comparisonService = comparisonService;
        this.stockRepository = stockRepository;
        this.stockPriceRepository = stockPriceRepository;
    }

    @GetMapping("/symbols")
    public ResponseEntity<List<Map<String, String>>> getStockSymbols() {
        List<Map<String, String>> symbols = stockService.getAllStocks().stream()
            .map(stock -> {
                Map<String, String> symbolInfo = new HashMap<>();
                symbolInfo.put("symbol", stock.getSymbol());
                symbolInfo.put("name", stock.getName());
                return symbolInfo;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(symbols);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStock(@PathVariable String symbol) {
        return stockService.findStock(symbol)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{symbol}/prices")
    public ResponseEntity<List<StockPrice>> getStockPrices(
            @PathVariable String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        try {
            List<StockPrice> prices = stockService.getStockPrices(symbol, startDate, endDate);
            return ResponseEntity.ok(prices);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/compare")
    public ResponseEntity<Map<String, List<StockPrice>>> compareStocks(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        
        try {
            Map<String, List<StockPrice>> result = comparisonService.compareStocks(symbol1, symbol2, from, to);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{symbol}/performance")
    public ResponseEntity<Map<String, Double>> getPerformance(
            @PathVariable String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        
        try {
            Map<String, Double> performanceMetrics = comparisonService.calculatePerformance(symbol, from, to);
            return ResponseEntity.ok(performanceMetrics);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/performance/compare")
    public ResponseEntity<Map<String, Map<String, Double>>> comparePerformance(
            @RequestParam List<String> symbols,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        
        try {
            Map<String, Map<String, Double>> performanceComparison = 
                comparisonService.comparePerformance(symbols, from, to);
            return ResponseEntity.ok(performanceComparison);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/initialize-sample-data")
    @Transactional
    public ResponseEntity<String> initializeSampleData() {
        try {
            // Clear existing data
            stockPriceRepository.deleteAll();
            stockRepository.deleteAll();
            
            // Create sample stocks
            Stock apple = new Stock("AAPL", "Apple Inc.", "NASDAQ");
            Stock microsoft = new Stock("MSFT", "Microsoft Corporation", "NASDAQ");
            Stock google = new Stock("GOOGL", "Alphabet Inc.", "NASDAQ");
            
            stockRepository.save(apple);
            stockRepository.save(microsoft);
            stockRepository.save(google);
            
            // Current date range
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(30);
            
            // Sample data for Apple
            addSamplePrices(apple, startDate, endDate, 170.0);
            
            // Sample data for Microsoft
            addSamplePrices(microsoft, startDate, endDate, 330.0);
            
            // Sample data for Google
            addSamplePrices(google, startDate, endDate, 140.0);
            
            return ResponseEntity.ok("Sample data initialized successfully!");
        } catch (Exception e) {
            logger.severe("Error initializing sample data: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Error initializing sample data: " + e.getMessage());
        }
    }
    
    private void addSamplePrices(Stock stock, LocalDate startDate, LocalDate endDate, double startingPrice) {
        Random random = new Random(stock.getSymbol().hashCode()); // Use symbol as seed for reproducible data
        double currentPrice = startingPrice;
        
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
        }
        
        logger.info("Added sample price data for " + stock.getSymbol());
    }
    
    // Helper method to round to 2 decimal places
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
} 