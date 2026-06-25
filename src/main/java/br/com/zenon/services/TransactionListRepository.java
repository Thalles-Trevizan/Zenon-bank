package br.com.zenon.services;

import br.com.zenon.records.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private final List<Transaction> transactions;

    public TransactionListRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> findByOriginName(String client) {
        Optional<Transaction> transaction = transactions.stream()
                .filter(t ->
                    t.origin().name().equals(client))
                .findFirst();

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
