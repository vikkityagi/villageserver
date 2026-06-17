package com.marketplace.village.dto;



import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.marketplace.village.entity.Category;
import com.marketplace.village.entity.Shop;

import lombok.Data;



@Data
public class ShopUserDto {

    private UUID id;

    private String name;
    private String shopAddress;
    private String contact;
    private String altContact;

    private Boolean isActive;

    private Boolean isDelete;

    private Boolean deliveryAvailable;

    private String password;
    private String confirmPassword;

    // private Long shopId; // instead of full Shop object

    private String pinCode;

    private String profileImageUrl; // Base64 string

    private LocalDateTime lastTimeActivity;

    private String email;

    private List<Category> category;

    private List<ShopDto> shops;

    

    
}
