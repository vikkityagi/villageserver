package com.marketplace.village.dto;



import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ShopDto {

    private UUID id;

    private String shopName;
    private String shopAddress;
    private String contact;
    private String altContact;

    private Boolean isActive;
    private Boolean deliveryAvailable;
    private Double deliveryCharge;

    private String pinCode;
    private String shopImage;
    private String role;

    private String shopDescription;
    // private String shopCategory;
    private String openTime;
    private String closeTime;

    private String shopLicenseNumber;
    private String shopRegistrationNumber;
    private String shopGSTNumber;
    private String shopPanNumber;
    private String shopAadharNumber;
    private String shopFSSAINumber;

    private String shopBankAccountNumber;
    private String shopIFSCCode;
    private String shopOwnerName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean isDeleted;

    // ✅ Nested DTOs
    private UUID category;
    private UUID shopUser;
    private List<UUID> products;
}
