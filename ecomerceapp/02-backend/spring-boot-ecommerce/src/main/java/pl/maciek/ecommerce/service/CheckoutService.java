package pl.maciek.ecommerce.service;

import pl.maciek.ecommerce.dto.Purchase;
import pl.maciek.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
