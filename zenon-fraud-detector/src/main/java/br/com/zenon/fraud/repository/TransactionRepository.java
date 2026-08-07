package br.com.zenon.fraud.repository;

import br.com.zenon.fraud.model.record.Transaction;

import java.util.Optional;

public interface TransactionRepository {
    Optional<Transaction> findByOriginName(String originName);
}
