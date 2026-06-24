package br.com.zenon.records;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.isNull;

public record TransactionCustomer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance
) {

    public TransactionCustomer {
        Objects.requireNonNull(oldBalance, "oldBalance is null");
        Objects.requireNonNull(newBalance, "newBalance is null");

        if (isNull(name) || name.isBlank()) throw new IllegalArgumentException("O name nao pode ser vazio: " + name);
        if (oldBalance.signum() < 0)
            throw new IllegalArgumentException("O valor de oldBalance deve ser positivo ou zero: " + oldBalance);
        if (newBalance.signum() < 0)
            throw new IllegalArgumentException("O valor de newBalance deve ser positivo ou zero: " + newBalance);
    }
}
