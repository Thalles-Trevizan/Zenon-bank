package br.com.zenon.services;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    private record ReportTransaction(BigDecimal amount, boolean isFraud) {
    }

    public record Statistics(long totalTransactions, long totalFrauds, BigDecimal totalAmount) {

        private final static Statistics ZERO = new Statistics(0, 0, BigDecimal.ZERO);

        private Statistics addReportTransaction(ReportTransaction rt) {
            return new Statistics(
                    totalTransactions + 1,
                    totalFrauds + (rt.isFraud ? 1 : 0),
                    totalAmount.add(rt.amount));
        }

        private Statistics add(Statistics other) {
            return new Statistics(
                    totalTransactions + other.totalTransactions,
                    totalFrauds + other.totalFrauds,
                    totalAmount.add(other.totalAmount));
        }
    }


    public Statistics generateReport(String fileName) {
        Path path = Path.of(fileName);

        try {
            Stream<String> lines = Files.lines(path);
            return lines
                    .skip(1)
                    .map(this::parseReportTransactional)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(Statistics.ZERO,
                            Statistics::addReportTransaction,
                            Statistics::add);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + fileName);
        }
    }


    private Optional<ReportTransaction> parseReportTransactional(String line) {

        try {
            String[] chunks = line.split(",");

            if (chunks[2] == null || chunks[2].trim().isBlank())
                throw new IllegalArgumentException("o valor do amount está zerado");
            BigDecimal amount = new BigDecimal(chunks[2]);

            boolean isFraud = "1".equals(chunks[9]);

            return Optional.of(new ReportTransaction(amount, isFraud));

        } catch (Exception e) {
            System.out.println("Erro ao fazer parse: " + line + " | " + e.getMessage());
        }

        return Optional.empty();
    }
}