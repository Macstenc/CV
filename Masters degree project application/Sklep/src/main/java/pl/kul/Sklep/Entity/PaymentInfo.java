package pl.kul.Sklep.Entity;

import lombok.Data;

@Data
public class PaymentInfo {
    private String paymentMethod;
    private String transactionId;
    private boolean isPaid;
}
