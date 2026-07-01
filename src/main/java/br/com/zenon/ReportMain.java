package br.com.zenon;

import br.com.zenon.services.TransactionReport;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    void main(String[] args) {
        // Escolhendo localidade
        String language = (args.length > 0) ? args[0] : "pt";
        Locale locale = Locale.of(language);
        
        // Formatando valores e moedas
        NumberFormat integerFormatter = NumberFormat.getIntegerInstance(locale);
        NumberFormat currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
        currencyFormatter.setCurrency(Currency.getInstance("USD"));

        // Utilizando bundle para tradução de texto
        ResourceBundle resourceBundle = ResourceBundle.getBundle("report", locale);

        // Gerando o relatório
        TransactionReport transactionReport = new TransactionReport();
        TransactionReport.Statistics statistics = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");

        // Transformando os dados retornados para o padrão escolhido
        String fmtTotalTransactions = integerFormatter.format(statistics.totalTransactions());
        String fmtTotalFrauds = integerFormatter.format(statistics.totalFrauds());
        String fmtTotalAmount = currencyFormatter.format(statistics.totalAmount());
        
        // Definindo textos a serem usados
        String msgTotalTransactions = resourceBundle.getString("label.total.transactions");
        String msgTotalfrauds = resourceBundle.getString("label.total.frauds");
        String msgTotalamount = resourceBundle.getString("label.total.amount");

        System.out.printf("""
                %s: %s
                %s: %s
                %s: %s
                %n""",
                msgTotalTransactions, fmtTotalTransactions,
                msgTotalfrauds, fmtTotalFrauds,
                msgTotalamount, fmtTotalAmount);
    }
}
