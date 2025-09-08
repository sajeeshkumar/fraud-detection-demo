package ai.streamml4j.fraud;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FraudDetector detector = new FraudDetector();
        List<Transaction> transactions = FraudDataGenerator.generate(500);

        detector.run(transactions);
    }
}
