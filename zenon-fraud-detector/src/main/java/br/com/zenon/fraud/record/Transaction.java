package br.com.zenon.fraud.record;

import br.com.zenon.fraud.enums.TypeTransaction;

import java.math.BigDecimal;

public record Transaction(int step, TypeTransaction type, BigDecimal amount, Customer origin, Customer recipient,
                          boolean isFraud,
                          boolean isFlaggedFraud) {
}
