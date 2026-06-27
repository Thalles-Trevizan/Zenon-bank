package br.com.zenon;

import br.com.zenon.services.TransactionReport;

public class ReportMain {

    void main() {
        TransactionReport transactionReport = new TransactionReport();
        TransactionReport.Statistics statistics = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        System.out.printf("""
                Total de linhas: %d
                Total de fraudes: %d
                Valor total transacionado: %.2f
                %n""", statistics.totalTransactions(), statistics.totalFrauds(), statistics.totalAmount());
    }
}
