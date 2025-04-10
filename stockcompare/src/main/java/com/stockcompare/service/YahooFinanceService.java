package com.stockcompare.service;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class YahooFinanceService {

    public Stock getStock(String symbol) {
        try {
            yahoofinance.Stock yahooStock = YahooFinance.get(symbol);
            if (yahooStock != null) {
                return new Stock(
                    yahooStock.getSymbol(),
                    yahooStock.getName(),
                    yahooStock.getStockExchange()
                );
            }
        } catch (IOException e) {
            log.error("Error fetching stock information for symbol: " + symbol, e);
        }
        return null;
    }

    public List<StockPrice> getHistoricalPrices(Stock stock, LocalDate startDate, LocalDate endDate) {
        List<StockPrice> prices = new ArrayList<>();
        
        try {
            Calendar from = Calendar.getInstance();
            from.setTime(java.sql.Date.valueOf(startDate));
            
            Calendar to = Calendar.getInstance();
            to.setTime(java.sql.Date.valueOf(endDate));
            
            yahoofinance.Stock yahooStock = YahooFinance.get(stock.getSymbol());
            List<HistoricalQuote> quotes = yahooStock.getHistory(from, to, Interval.DAILY);
            
            for (HistoricalQuote quote : quotes) {
                LocalDate date = quote.getDate().getTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
                
                StockPrice price = new StockPrice(
                    stock,
                    date,
                    quote.getOpen() != null ? quote.getOpen().doubleValue() : 0.0,
                    quote.getHigh() != null ? quote.getHigh().doubleValue() : 0.0,
                    quote.getLow() != null ? quote.getLow().doubleValue() : 0.0,
                    quote.getClose() != null ? quote.getClose().doubleValue() : 0.0,
                    quote.getVolume() != null ? quote.getVolume() : 0
                );
                
                prices.add(price);
            }
        } catch (IOException e) {
            log.error("Error fetching historical prices for symbol: " + stock.getSymbol(), e);
        }
        
        return prices;
    }
} 