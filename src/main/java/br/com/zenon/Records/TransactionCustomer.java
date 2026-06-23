package br.com.zenon.Records;

import java.math.BigDecimal;

public record TransactionCustomer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance
) {
}
