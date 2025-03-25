public class Share {
    private StockCompany stockCompany;
    private int quantity;
    private double price;

    public Share(StockCompany stockCompany, int quantity, double price) {
        this.stockCompany = stockCompany;
        this.quantity = quantity;
        this.price = price;
    }

    public double calculateTotalValue() {
        return quantity * price;
    }

    public StockCompany getStockCompany() {
        return stockCompany;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
