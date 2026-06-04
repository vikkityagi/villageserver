package com.marketplace.village.dto;



import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CustomerDto {

    private UUID id;

    private String customerName;
    private String customerPhone;
    private String password;
    private String confirmPassword;
    private String customerAddress;
    private String customerPinCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Instead of full Shop object
    private UUID shopId;

    // Instead of List<Product>
    private List<UUID> productIds;

    private Boolean isActive;
    private Boolean isDeleted;
}
