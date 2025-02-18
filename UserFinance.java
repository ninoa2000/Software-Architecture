public class UserFinance extends FinancialEntity {
    public UserFinance(Data data) {
        super(data);
    }

    @Override
    public void storeData() {
        data.storeData("User finance data stored.");
    }

    public void retrieveFinanceData() {
        data.retrieveData();
    }
}
