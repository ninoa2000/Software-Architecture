public class Transaction {
    private Stock stock;
    private int quantity;
    private String date;

    public Transaction(Stock stock, int quantity, String date) {
        this.stock = stock;
        this.quantity = quantity;
        this.date = date;
    }

    public String getTransactionDetails() {
        return "Transaction: " + quantity + " of " + stock.getStockDetails() + " on " + date;
    }
}
