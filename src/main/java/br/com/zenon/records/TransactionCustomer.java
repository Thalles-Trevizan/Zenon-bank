package br.com.zenon.records;

import java.math.BigDecimal;

public record TransactionCustomer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance) {
}
