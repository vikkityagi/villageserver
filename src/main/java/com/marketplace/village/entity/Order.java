package com.marketplace.village.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Shop shop;

    private String customerName;
    private String customerPhone;
    private String customerAddress;

    private String deliveryType; // PICKUP / DELIVERY
    private Double totalAmount;

    private String status; // PLACED, ACCEPTED
}
