package com.marketplace.village.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderItemDto {
    private UUID shopProductId;
    private Integer quantity;
}