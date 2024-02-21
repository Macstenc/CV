package pl.kul.kulzaki.Models;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Transaction {
    private String senderAccount;
    private String receiverAccount;
    private int amount;

    public Transaction(String senderAccount, String receiverAccount, int amount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
    }
}
