package br.com.zenon.fraud.model.record;

import br.com.zenon.fraud.model.enums.TypeTransaction;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(int step, TypeTransaction type, BigDecimal amount, Customer origin, Customer recipient,
                          boolean isFraud,
                          boolean isFlaggedFraud) {
    public Transaction {
        Objects.requireNonNull(type);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(origin);
        Objects.requireNonNull(recipient);
        if (step <= 0) throw new IllegalArgumentException("O valor de step deve ser positivo: " + step);
        if (amount.signum() < 0) throw new IllegalArgumentException("O valor de amount deve ser positivo: " + amount);

    }
}
