package ai.streamml4j.fraud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FraudDataGenerator {
    private static final Random rand = new Random();

    private static final List<String> SAFE_COUNTRIES = Arrays.asList("US", "CA", "UK");
    private static final List<String> RISKY_COUNTRIES = Arrays.asList("RU", "NG", "BR");

    public static List<Transaction> generate(int n) {
        List<Transaction> txns = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            boolean fraud = rand.nextDouble() < 0.3; // ~30% fraud
            double amount;
            int hour;
            String country;

            if (fraud) {
                amount = 1000 + rand.nextDouble() * 9000;  // high
                hour = rand.nextBoolean() ? rand.nextInt(6) : (22 + rand.nextInt(2)); // late/early
                country = RISKY_COUNTRIES.get(rand.nextInt(RISKY_COUNTRIES.size()));
            } else {
                amount = 5 + rand.nextDouble() * 200;  // small
                hour = 8 + rand.nextInt(10);           // business hours
                country = SAFE_COUNTRIES.get(rand.nextInt(SAFE_COUNTRIES.size()));
            }

            txns.add(new Transaction("txn" + i, amount, hour, country, fraud ? 1 : 0));
        }

        return txns;
    }
}
