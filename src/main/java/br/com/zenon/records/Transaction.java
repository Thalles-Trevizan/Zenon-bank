package br.com.zenon.records;

import br.com.zenon.enums.TransactionType;

import java.math.BigDecimal;

public record Transaction(
        int step,
        TransactionType type,
        BigDecimal amount,
        br.com.zenon.records.TransactionCustomer origin,
        br.com.zenon.records.TransactionCustomer recipient,
        boolean isFraud,
        boolean isFlaggedFraud
) {
}
