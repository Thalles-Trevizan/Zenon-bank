package br.com.zenon.services;

import br.com.zenon.records.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepository {

    private final Map<String, Transaction> transactionByOriginName;

    public TransactionMapRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactionByOriginName = transactions.stream()
                .collect(
                        Collectors.toMap(t -> t.origin().name(),
                                Function.identity()));
    }

    @Override
    public Optional<Transaction> findByOriginName(String client) {
        Optional<Transaction> transaction = Optional.ofNullable(transactionByOriginName.get(client));

        if  (transaction.isPresent()) {
            System.out.println("Transação do cliente " + client + ":");
            transaction.stream().forEach(System.out::println);
            return transaction;
        } else {
            System.out.println("Transação não encontrada para o cliente  " + client + ":");
            return Optional.empty();
        }
    }
}
