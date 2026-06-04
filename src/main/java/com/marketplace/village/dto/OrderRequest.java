package com.marketplace.village.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class OrderRequest {

    private UUID shopId;
    private String deliveryType;

    private String customerName;
    private String customerPhone;
    private String customerAddress;

    private List<OrderItemDto> items;
}
