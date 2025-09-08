package ai.streamml4j.fraud;

import ai.streamml4j.algorithms.classification.OnlineLogisticRegression;
import ai.streamml4j.core.Instance;
import ai.streamml4j.metrics.Accuracy;

import java.util.Arrays;
import java.util.List;


public class FraudDetector {
    private final OnlineLogisticRegression model;
    private final Accuracy accuracy;

    // Features: [scaled amount, normalized hour, riskyCountry]
    public FraudDetector() {
        this.model = new OnlineLogisticRegression(
                3,      // number of features
                0.1,    // learning rate
                0.01    // regularization
        );
        this.accuracy = new Accuracy();
    }

    private Instance toInstance(Transaction txn) {
        double scaledAmount = txn.getAmount() / 10000.0;       // scale into [0,1]
        double normHour = txn.getHourOfDay() / 24.0;           // normalize
        double risky = isRiskyCountry(txn.getCountry()) ? 1.0 : 0.0;

        double[] features = new double[] { scaledAmount, normHour, risky };
        double label = txn.getLabel() == 1 ? 1.0 : 0.0;
        return new Instance(features, label);
    }

    private boolean isRiskyCountry(String country) {
        List<String> risky = Arrays.asList("RU", "NG", "BR");
        return risky.contains(country);
    }

    public void run(List<Transaction> transactions) {
        int i = 1;
        for (Transaction txn : transactions) {
            Instance inst = toInstance(txn);

            double score = model.score(inst);   // fraud probability
            double pred = model.predict(inst);  // 0 or 1

            model.learn(inst);                  // online update
            accuracy.update(inst.label(), pred);

            System.out.printf(
                    "Txn %3d: amount=%.2f, hour=%d, country=%s | true=%.0f pred=%.0f score=%.2f acc=%.2f%%%n",
                    i++, txn.getAmount(), txn.getHourOfDay(), txn.getCountry(),
                    inst.label(), pred, score, accuracy.get() * 100.0
            );
        }

        System.out.printf("Final accuracy: %.2f%%%n", accuracy.get() * 100.0);
    }
}
