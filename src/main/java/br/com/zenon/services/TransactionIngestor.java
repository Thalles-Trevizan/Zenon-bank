package br.com.zenon.services;

import br.com.zenon.enums.TransactionType;
import br.com.zenon.records.Transaction;
import br.com.zenon.records.TransactionCustomer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TransactionIngestor {

    public List<Transaction> read(String fileName){
        Path path = Path.of(fileName);

        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::parseTransactional)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + fileName, e);
        }
    }

    private Transaction parseTransactional(String line) {
        String[] chunks = line.split(",");

        //Transactional commons
        int step = Integer.parseInt(chunks[0]);
        TransactionType type = TransactionType.valueOf(chunks[1]);
        BigDecimal amount = new BigDecimal(chunks[2]);

        boolean isFraud = "1".equals(chunks[9]);
        boolean isFlaggedFraud = "1".equals(chunks[10]);

        //Transactional Origin
        String nameOrigin = chunks[3];
        BigDecimal oldBalance = new BigDecimal(chunks[4]);
        BigDecimal newBalance = new BigDecimal(chunks[5]);

        //Transactional Recipient
        String nameRecipient = chunks[6];
        BigDecimal oldBalanceRecipient = new BigDecimal(chunks[7]);
        BigDecimal newBalanceRecipient = new BigDecimal(chunks[8]);

        TransactionCustomer origin = new TransactionCustomer(nameOrigin, oldBalance, newBalance);
        TransactionCustomer recipient = new TransactionCustomer(nameRecipient, oldBalanceRecipient, newBalanceRecipient);

        return new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
    }
}
