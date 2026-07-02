package br.com.zenon.interfaces;

import br.com.zenon.records.Transaction;

import java.util.Optional;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> findByOriginName(String client);
}
