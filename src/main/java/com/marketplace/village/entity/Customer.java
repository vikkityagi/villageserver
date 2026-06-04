package com.marketplace.village.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.marketplace.village.enums.Role;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue
    private UUID id;

    private String customerName;

    @Column(unique = true)
    private String customerPhone;

    private String password;

    // private String confirmPassword;

    private String customerAddress;

    private String customerPinCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ShopProduct> product;

    private Boolean isActive = true;
    private Boolean isDeleted = false;

    private Boolean orderFullFill = false;

    
}
