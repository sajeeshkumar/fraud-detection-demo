package ai.streamml4j.fraud;

public class Transaction {
    private final String id;
    private final double amount;     // transaction amount
    private final int hourOfDay;     // 0–23
    private final String country;    // country code
    private final int label;         // 1 = fraud, 0 = legit

    public Transaction(String id, double amount, int hourOfDay, String country, int label) {
        this.id = id;
        this.amount = amount;
        this.hourOfDay = hourOfDay;
        this.country = country;
        this.label = label;
    }

    public String getId() { return id; }
    public double getAmount() { return amount; }
    public int getHourOfDay() { return hourOfDay; }
    public String getCountry() { return country; }
    public int getLabel() { return label; }
}
