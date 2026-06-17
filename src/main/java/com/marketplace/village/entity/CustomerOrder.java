package com.marketplace.village.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.marketplace.village.enums.DeliveryOption;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CustomerOrder extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String customerName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String pinCode;

    @Column(nullable = false, length = 500)
    private String address;

    private String landmark;

    @Enumerated(EnumType.STRING)
    private DeliveryOption deliveryOption;

    private LocalDateTime orderDate;

    private Boolean isActive = true;

    private int quantity;

    private UUID shopId;

    private UUID productId;
}