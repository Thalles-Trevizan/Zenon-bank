package br.com.zenon;

import br.com.zenon.enums.TransactionType;
import br.com.zenon.interfaces.TransactionRepository;
import br.com.zenon.records.Transaction;
import br.com.zenon.records.TransactionCustomer;
import br.com.zenon.services.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {

    void main() {
        var t1 = new Transaction(
                1,
                TransactionType.PAYMENT,
                new BigDecimal("9839.64"),
                new TransactionCustomer("C1231006815",
                        new BigDecimal("170136.00"),
                        new BigDecimal("160296.36")),
                new TransactionCustomer("M1979787155",
                        new BigDecimal("0.00"),
                        new BigDecimal("0.00")),
                true,
                false
        );

        var t2 = new Transaction(
                743,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                new TransactionCustomer("C1280323807",
                        new BigDecimal("850002.52"),
                        new BigDecimal("0.00")),
                new TransactionCustomer("C873221189",
                        new BigDecimal("6510099.11"),
                        new BigDecimal("7360101.63")),
                true,
                false
        );

        System.out.println(t1);
        System.out.println(t2);

        System.out.println("===============================================");

        TransactionIngestor ingestor = new TransactionIngestor();
        List<Transaction> transactionsBadData = ingestor.read("data/paysim_with_bad_data.csv", 20);

        System.out.println(transactionsBadData.size());
        transactionsBadData
                .forEach(System.out::println);

        System.out.println("===============================================");

        List<Transaction> transactions = ingestor.read("data/PS_20174392719_1491204439457_log.csv", 1_000);
        transactions.stream()
                .limit(10)
                .forEach(System.out::println);

        System.out.println("===============================================");

        List<Transaction> transactionsForProcessing = ingestor.read("data/PS_20174392719_1491204439457_log.csv", 50_000);
        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(transactionsForProcessing);

        fraudAnalyzer.countFrauds();
        fraudAnalyzer.findHighestValueFrauds(3);
        fraudAnalyzer.findTopSuspiciousClients(5);
        fraudAnalyzer.calculateTotalFraudLoss();
        fraudAnalyzer.countFraudsByType();

        System.out.println("===============================================");

        List<Transaction> transaction100 = ingestor.read("data/PS_20174392719_1491204439457_log.csv", 100_000);
        TransactionRepository transactionBenchmark = new TransactionListRepository(transaction100);

        long startTimeList = System.currentTimeMillis();
        Optional<Transaction> c1231006815 = transactionBenchmark.findByOriginName("C1231006815");
        Optional<Transaction> c12345 = transactionBenchmark.findByOriginName("C12345");
        long endTimeList = System.currentTimeMillis();
        System.out.println("Total execution time for List: " + (endTimeList - startTimeList));

        //Map para uma busca de 100k já começa a ser bem mais rapido
        transactionBenchmark = new TransactionMapRepository(transaction100);
        long startTimeMap = System.currentTimeMillis();
        Optional<Transaction> c1 = transactionBenchmark.findByOriginName("C1231006815");
        Optional<Transaction> c2 = transactionBenchmark.findByOriginName("C12345");
        long endTimeMap = System.currentTimeMillis();
        System.out.println("Total execution time for Map: " + (endTimeMap - startTimeMap));
    }
}
