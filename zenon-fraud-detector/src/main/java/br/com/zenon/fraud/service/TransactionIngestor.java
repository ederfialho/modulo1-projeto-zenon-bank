package br.com.zenon.fraud.service;

import br.com.zenon.fraud.model.enums.TypeTransaction;
import br.com.zenon.fraud.model.record.Customer;
import br.com.zenon.fraud.model.record.Transaction;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionIngestor {

    private static final int LIMITADOR = 50000;

    public List<Transaction> readFile(String fileName) {
        Path path = Path.of(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(LIMITADOR)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Transaction> parseTransaction(String line) {
        try {
            String[] chunks = line.split(";");
            if (Objects.isNull(chunks[2]) || chunks[2].trim().isEmpty()) throw new IllegalArgumentException("O valor de amount não pode ser nulo ou vazio");
            return Optional.of(new Transaction(
                    Integer.parseInt(chunks[0]),
                    TypeTransaction.valueOf(chunks[1]),
                    new BigDecimal(chunks[2]),
                    new Customer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5])),
                    new Customer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8])),
                    Integer.parseInt(chunks[9]) == 1,
                    Integer.parseInt(chunks[10]) == 1));
        } catch (Exception e) {
            System.err.println("Erro ao fazer parse: " + line + " - " + e);
            return Optional.empty();
        }
    }

}
