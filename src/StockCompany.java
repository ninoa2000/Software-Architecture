public class StockCompany {
    private String companyName;
    private String tickerSymbol;

    public StockCompany(String companyName, String tickerSymbol) {
        this.companyName = companyName;
        this.tickerSymbol = tickerSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }
}
