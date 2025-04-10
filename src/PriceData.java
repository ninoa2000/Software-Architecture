public class PriceData {
    private StockCompany stockCompany;
    private double price;

    public PriceData(StockCompany stockCompany, double price) {
        this.stockCompany = stockCompany;
        this.price = price;
    }

    public StockCompany getStockCompany() {
        return stockCompany;
    }

    public double getPrice() {
        return price;
    }
}
