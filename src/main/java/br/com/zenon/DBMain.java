package br.com.zenon;

import br.com.zenon.databases.ConnectionFactory;
import br.com.zenon.records.Transaction;
import br.com.zenon.services.TransactionIngestor;
import br.com.zenon.services.TransactionSQLRepository;

import java.util.List;

public class DBMain {

    void main() {
        ConnectionFactory.getConnection();
        System.out.println("Banco de dados conectado com sucesso!");

        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();

        TransactionIngestor ingestor = new TransactionIngestor();

        long startTimeSQL = System.currentTimeMillis();
        List<Transaction> transaction10_000 = ingestor.read("data/PS_20174392719_1491204439457_log.csv", 10_000);
        System.out.println(transaction10_000.size());

        System.out.println("Iniciando add");
        transaction10_000.forEach(transactionSQLRepository::save);

        long endTimeSQL = System.currentTimeMillis();
        System.out.println("Total execution time for SQL: " + (endTimeSQL - startTimeSQL));

        transactionSQLRepository.findByOriginName("C1231006815")
                        .ifPresentOrElse(System.out::println, () -> System.out.println("transacao não encontrada para: C1231006815"));

    }
}
