package br.com.zenon.services;

import br.com.zenon.records.Transaction;

import java.util.Optional;

public interface TransactionRepository {
    Optional<Transaction> findByOriginName(String client);
}
