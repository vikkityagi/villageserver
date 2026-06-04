package com.marketplace.village.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ShopProductDto {
    
    
        private UUID id;
    
        
        
        private UUID shopId;
    
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
        private String shopUserPinCode;
        private String shopAddress;
        private String shopContact;
        private String shopUserAddress;
    
        
    
}


