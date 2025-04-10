package com.stockcompare.service;

import com.stockcompare.model.StockPrice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Implementation of the ComparisonServiceInterface.
 * This service handles all stock comparison operations.
 */
@Service
public class ComparisonService implements ComparisonServiceInterface {

    private static final Logger logger = Logger.getLogger(ComparisonService.class.getName());
    private final StockServiceInterface stockService;

    public ComparisonService(StockServiceInterface stockService) {
        this.stockService = stockService;
    }

    @Override
    public Map<String, List<StockPrice>> compareStocks(
            String symbol1, 
            String symbol2, 
            LocalDate from, 
            LocalDate to) {
        
        try {
            logger.info("Comparing stocks " + symbol1 + " and " + symbol2 + 
                        " from " + from + " to " + to);
            
            List<StockPrice> prices1 = stockService.getStockPrices(symbol1, from, to);
            List<StockPrice> prices2 = stockService.getStockPrices(symbol2, from, to);
            
            Map<String, List<StockPrice>> result = new HashMap<>();
            result.put(symbol1, prices1);
            result.put(symbol2, prices2);
            
            return result;
        } catch (Exception e) {
            logger.severe("Error comparing stocks: " + e.getMessage());
            throw new IllegalArgumentException("Error comparing stocks: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Double> calculatePerformance(
            String symbol,
            LocalDate from,
            LocalDate to) {
        
        try {
            logger.info("Calculating performance for " + symbol + 
                        " from " + from + " to " + to);
            
            List<StockPrice> prices = stockService.getStockPrices(symbol, from, to);
            
            if (prices.isEmpty()) {
                throw new IllegalArgumentException("No price data available for " + symbol);
            }
            
            Map<String, Double> metrics = new HashMap<>();
            
            // Calculate starting and ending prices
            double startPrice = prices.get(0).getClose();
            double endPrice = prices.get(prices.size() - 1).getClose();
            
            // Percent change
            double percentChange = ((endPrice - startPrice) / startPrice) * 100;
            metrics.put("percentChange", Math.round(percentChange * 100.0) / 100.0);
            
            // Absolute change
            double absoluteChange = endPrice - startPrice;
            metrics.put("absoluteChange", Math.round(absoluteChange * 100.0) / 100.0);
            
            // Volatility (standard deviation of daily returns)
            double[] dailyReturns = new double[prices.size() - 1];
            for (int i = 1; i < prices.size(); i++) {
                dailyReturns[i - 1] = (prices.get(i).getClose() - prices.get(i - 1).getClose()) 
                                      / prices.get(i - 1).getClose();
            }
            
            double mean = 0;
            for (double dailyReturn : dailyReturns) {
                mean += dailyReturn;
            }
            mean = dailyReturns.length > 0 ? mean / dailyReturns.length : 0;
            
            double variance = 0;
            for (double dailyReturn : dailyReturns) {
                variance += Math.pow(dailyReturn - mean, 2);
            }
            variance = dailyReturns.length > 0 ? variance / dailyReturns.length : 0;
            
            double volatility = Math.sqrt(variance) * 100; // Convert to percentage
            metrics.put("volatility", Math.round(volatility * 100.0) / 100.0);
            
            // Add high and low during period
            double high = prices.stream().mapToDouble(StockPrice::getHigh).max().orElse(0);
            double low = prices.stream().mapToDouble(StockPrice::getLow).min().orElse(0);
            metrics.put("highPrice", high);
            metrics.put("lowPrice", low);
            
            return metrics;
        } catch (Exception e) {
            logger.severe("Error calculating performance: " + e.getMessage());
            throw new IllegalArgumentException("Error calculating performance: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Map<String, Double>> comparePerformance(
            List<String> symbols,
            LocalDate from,
            LocalDate to) {
        
        try {
            logger.info("Comparing performance for " + symbols + 
                        " from " + from + " to " + to);
            
            return symbols.stream()
                .collect(Collectors.toMap(
                    symbol -> symbol, 
                    symbol -> calculatePerformance(symbol, from, to),
                    (v1, v2) -> v1,
                    HashMap::new
                ));
            
        } catch (Exception e) {
            logger.severe("Error comparing performance: " + e.getMessage());
            throw new IllegalArgumentException("Error comparing performance: " + e.getMessage());
        }
    }
} 