package com.marketplace.village.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class Shop {

    @Id
    @GeneratedValue
    private UUID id;

    private String shopName;
    private String shopAddress;
    private String contact;
    private String altContact;

    private Boolean isActive = true;

    private Boolean deliveryAvailable;
    private Double deliveryCharge;
    // private String password;

    // ✅ Many shop → one category (OWNER)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // ✅ One shop → many products
    @OneToMany(mappedBy = "shop")
    private List<ShopProduct> products;

    @ManyToOne
    private ShopUser shopUser;

    private String pinCode;

    @Column(columnDefinition = "BYTEA")
    private byte[] shopImage;

    private String role; // e.g., OWNER, MANAGER, STAFF
    private String shopDescription;
    private String openTime; // e.g., 9 AM - 9 PM
    private String closeTime; // e.g., 9 AM - 9 PM
    private String shopLicenseNumber; // for regulatory purposes
    private String shopRegistrationNumber; // for business registration
    private String shopGSTNumber; // for tax purposes
    private String shopPanNumber; // for tax purposes
    private String shopAadharNumber; // for identity verification
    private String shopFSSAINumber; // for food businesses
    private String shopBankAccountNumber; // for payments
    private String shopIFSCCode; // for payments
    private String shopOwnerName; // for display and contact purposes

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    // private String confirmPassword;
}
