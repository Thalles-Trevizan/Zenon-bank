package br.com.zenon.services;

import br.com.zenon.records.Transaction;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public void countFrauds() {
        long transactionsWithFraud = transactions.stream()
                .filter(Transaction::isFraud)
                .count();

        System.out.println("Transações com Fraude encontradas: " + transactionsWithFraud);
    }

    public void findHighestValueFrauds(int limit) {

        System.out.println("Top " + limit + " fraudes com maior valor:");
        transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount)
                        .reversed())
                .limit(limit)
                .map(Transaction::amount)
                .map(BigDecimal::toPlainString)
                .forEach(System.out::println);
    }

    public void findTopSuspiciousClients(int limit) {
        System.out.println("Top " + limit + " Clientes Suspeitos: ");
        transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(transaction -> transaction.origin().name())
                .distinct()
                .limit(limit)
                .forEach(System.out::println);
    }

    public void calculateTotalFraudLoss() {
        BigDecimal totalLoss = transactions.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Prejuízo Total: " + totalLoss);
    }

    public void countFraudsByType() {
        System.out.println("Fraudes por tipo: ");
        transactions.stream()
                .filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(
                        Transaction::type,
                        Collectors.counting()))
                .forEach((type, quantity) -> {
                            System.out.println(type + ": " + quantity);
                        }
                );
    }
}
