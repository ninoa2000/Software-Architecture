// Abstract class for financial entities
abstract class FinancialEntity {
    protected Data data;

    public FinancialEntity(Data data) {
        this.data = data;
    }

    public abstract void storeData(); // Abstract method for storing data
}
