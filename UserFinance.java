<<<<<<< HEAD
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
=======
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
>>>>>>> c06d3e0488ed528f9b7ad836fba2b179e4b76c76
