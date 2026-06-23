package br.com.zenon;

import br.com.zenon.Enums.TransactionType;
import br.com.zenon.Records.Transaction;
import br.com.zenon.Records.TransactionCustomer;

import java.math.BigDecimal;

public class Main {

    void main(){
        var t1 = new Transaction(
                1,
                TransactionType.PAYMENT,
                new BigDecimal("9839.64"),
                new TransactionCustomer("C1231006815",
                    new BigDecimal("170136.00"),
                    new BigDecimal("160296.36")),
                new TransactionCustomer("M1979787155",
                    new BigDecimal("0.00"),
                    new BigDecimal("0.00")),
                true,
                false
        );

        var t2 = new Transaction(
                743,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                new TransactionCustomer("C1280323807",
                    new BigDecimal("850002.52"),
                    new BigDecimal("0.00")),
                new TransactionCustomer("C873221189",
                    new BigDecimal("6510099.11"),
                    new BigDecimal("7360101.63")),
                true,
                false
        );

        System.out.println(t1);
        System.out.println(t2);
    }
}
