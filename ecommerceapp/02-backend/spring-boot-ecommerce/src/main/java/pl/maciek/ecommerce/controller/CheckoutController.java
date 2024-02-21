package pl.maciek.ecommerce.controller;

import org.springframework.web.bind.annotation.*;
import pl.maciek.ecommerce.dto.Purchase;
import pl.maciek.ecommerce.dto.PurchaseResponse;
import pl.maciek.ecommerce.service.CheckoutService;


@RestController
@RequestMapping("/api/checkout")

public class CheckoutController {
    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService){
        this.checkoutService=checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }
}
