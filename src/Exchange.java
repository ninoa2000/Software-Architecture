public class Exchange {
    private String name;
    private String location;

    public Exchange(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getExchangeDetails() {
        return "Exchange: " + name + ", Location: " + location;
    }
}
