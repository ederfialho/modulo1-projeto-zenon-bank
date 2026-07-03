package br.com.zenon.fraud;

import br.com.zenon.fraud.model.enums.TypeTransaction;
import br.com.zenon.fraud.model.record.Customer;
import br.com.zenon.fraud.model.record.Transaction;
import br.com.zenon.fraud.service.FraudAnalyzer;
import br.com.zenon.fraud.service.TransactionIngestor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {

    void main() {
        var firstTransaction = new Transaction(1, TypeTransaction.PAYMENT, new BigDecimal("9839.64"),
                new Customer("C1231006815",new BigDecimal("170136.0"),new BigDecimal("160296.36")),
                new Customer("M1979787155",new BigDecimal("0.0"),new BigDecimal("0.36")),
                false,false);
        var secondTransaction = new Transaction(743, TypeTransaction.CASH_OUT, new BigDecimal("850002.52"),
                new Customer("C1280323807",new BigDecimal("850002.52"),new BigDecimal("0.0")),
                new Customer("C873221189",new BigDecimal("6510099.11"),new BigDecimal("7360101.63")),
                true,false);

        IO.println(firstTransaction);
        IO.println(secondTransaction);


        IO.println("------------------------------------------------------------------------------------------------------------------------------------------");

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions =  transactionIngestor.readFile("data/PS_20174392719_1491204439457_log.csv");

        transactions.stream().limit(10).forEach(IO::println);

        IO.println("------------------------------------------------------------------------------------------------------------------------------------------");

        List<Transaction> transactionsErr =  transactionIngestor.readFile("data/paysim_with_bad_data.txt");

        IO.println(transactionsErr.size());
        transactionsErr.forEach(IO::println);

        IO.println("--------------------------------------------------------------");

        var fraudAnalyzer = new FraudAnalyzer(transactions);

        long fraudCount = fraudAnalyzer.countFrauds();
        IO.println("1. Total de fraudes: " + fraudCount);

        List<BigDecimal> highestFraudAmounts = fraudAnalyzer.findHighestValueFraudAmounts(3);
        IO.println("2. Top 3 fraudes de maior valor:");
        highestFraudAmounts.forEach(amount -> IO.println("- %.2f".formatted(amount)));

        List<String> suspiciousClients = fraudAnalyzer.findTopSuspiciousClients(5);
        IO.println("3. Clientes suspeitos:");
        suspiciousClients.forEach(IO::println);

        BigDecimal totalFraudLoss = fraudAnalyzer.calculateTotalFraudLoss();
        IO.println("4. Prejuízo total: " + totalFraudLoss);

        Map<TypeTransaction, Long> fraudCountByType = fraudAnalyzer.countFraudsByType();
        IO.println("5. Fraudes por tipo:");
        fraudCountByType.forEach((type, count) -> IO.println("- %s: %d".formatted(type, count)));

    }
}
