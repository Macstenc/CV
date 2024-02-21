package pl.kul.kulzaki.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class Transaction {
    private String senderAccount;
    private String receiverAccount;
    private double amount;
}
