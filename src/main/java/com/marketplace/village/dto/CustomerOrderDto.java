package com.marketplace.village.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.marketplace.village.enums.DeliveryOption;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerOrderDto {

    private UUID id;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
    private String pinCode;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Landmark is required")
    private String landmark;

    private DeliveryOption deliveryOption;

    private UUID shopId;

    private UUID productId;

    private String productName;

    private Double totalproductPrice;

    private Double unitProductPrice;

    private String productUnit;

    private LocalDateTime orderDate;

    private LocalDateTime updatedAt;

    private Boolean isDeliver;

    private Boolean isActive;
}