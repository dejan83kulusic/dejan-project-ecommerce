package com.dejan.ecommerce.service;

import com.dejan.ecommerce.dto.Purchase;
import com.dejan.ecommerce.dto.PurchaseResponse;

import java.util.List;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);


}
