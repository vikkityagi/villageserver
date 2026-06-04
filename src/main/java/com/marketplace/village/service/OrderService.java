package com.marketplace.village.service;

import com.marketplace.village.dto.OrderRequest;

public interface OrderService {
    Object placeOrder(OrderRequest request);
}