public class PreferredShare extends Share {
    private double preferredDividend;

    public PreferredShare(StockCompany stockCompany, int quantity, double price, double preferredDividend) {
        super(stockCompany, quantity, price);
        this.preferredDividend = preferredDividend;
    }

    @Override
    public double calculateTotalValue() {
        return super.calculateTotalValue() + preferredDividend;
    }

    public double getPreferredDividend() {
        return preferredDividend;
    }
}
