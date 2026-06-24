package br.com.zenon.fraud.service;

import br.com.zenon.fraud.model.enums.TypeTransaction;
import br.com.zenon.fraud.model.record.Customer;
import br.com.zenon.fraud.model.record.Transaction;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TransactionIngestor {

    public List<Transaction> readFile(String fileName) {
        Path path = Path.of(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::parseTransaction)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction parseTransaction(String line) {
        String[] chunks = line.split(";");
        return new Transaction(
                Integer.parseInt(chunks[0]),
                TypeTransaction.valueOf(chunks[1]),
                new BigDecimal(chunks[2]),
                new Customer(chunks[3],new BigDecimal(chunks[4]),new BigDecimal(chunks[5])),
                new Customer(chunks[6],new BigDecimal(chunks[7]),new BigDecimal(chunks[8])),
                Integer.parseInt(chunks[9]) == 1,
                Integer.parseInt(chunks[10]) == 1);
    }

}
