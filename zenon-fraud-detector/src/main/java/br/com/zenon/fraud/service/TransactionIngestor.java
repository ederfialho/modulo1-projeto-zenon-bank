package br.com.zenon.fraud.service;

import br.com.zenon.fraud.model.enums.TypeTransaction;
import br.com.zenon.fraud.model.record.Customer;
import br.com.zenon.fraud.model.record.Transaction;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionIngestor {

    public List<Transaction> readFile(String fileName) {

        List<Transaction> transactions = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileName);
             Scanner scanner = new Scanner(fis)) {

            int lineCount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineCount++;

                if (lineCount == 1) {
                    continue;
                } else if (lineCount > 1001) {
                    break;
                }
                String[] chunks = line.split(";");
                transactions.add(new Transaction(
                        Integer.parseInt(chunks[0]),
                        TypeTransaction.valueOf(chunks[1]),
                        new BigDecimal(chunks[2]),
                        new Customer(chunks[3],new BigDecimal(chunks[4]),new BigDecimal(chunks[5])),
                        new Customer(chunks[6],new BigDecimal(chunks[7]),new BigDecimal(chunks[8])),
                        Integer.parseInt(chunks[9]) == 0,
                        Integer.parseInt(chunks[10]) == 0
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Deu ruim no arquivo "+fileName+" . Depois dá uma olhada no arquivo, ok?", e);
        }

        return transactions;
    }

}
