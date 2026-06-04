package com.marketplace.village.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ShopProduct {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "shop_id") // foreign key
    private Shop shop;

    private String productName;
    private Double productPrice;
    private Integer productQuantity;
    private Boolean isActive;

    private String productDescription;
    private String productImageUrl;
    // private String productCategory;
    private String productUnit; // e.g., kg, liter, piece
    // private String productBrand;
    // private String productCode; // unique code for the product
    private String productPinCode; // pin code for delivery area

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean isDelete = false;
}