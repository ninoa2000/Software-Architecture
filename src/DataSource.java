public class DataSource {
    private String sourceName;

    public DataSource(String sourceName) {
        this.sourceName = sourceName;
    }

    public PriceData getPriceData(StockCompany stockCompany) {
        // Simulate fetching price data
        return new PriceData(stockCompany, 100.0);  // Example static price
    }

    public String getSourceName() {
        return sourceName;
    }
}
