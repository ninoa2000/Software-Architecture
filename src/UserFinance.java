public class UserFinance {
    private Data data;

    public UserFinance(Data data) {
        this.data = data;
    }

    public void storeFinanceData() {
        data.storeData("User finance data stored.");
    }

    public void retrieveFinanceData() {
        data.retrieveData();
    }
}
