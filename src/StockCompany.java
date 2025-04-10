public class StockCompany implements StockInfo {
    private String name;
    private String symbol;

    public StockCompany(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getStockDetails() {
        return "Stock Company: " + name + ", Symbol: " + symbol;
    }
}
