package com.marketplace.village.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ShopUser {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String shopAddress;

    @Column(unique = true,nullable=false)
    private String contact;
    
    private String altContact;

    private Boolean isActive = true;

    private Boolean deliveryAvailable;
    // private Double deliveryCharge;
    private String password;
    private String confirmPassword;

    // @OneToMany(mappedBy = "shopUser")
    // private Shop shop;

    @OneToMany(mappedBy = "shopUser")
    private List<Category> category;

    private String pinCode;
    // private String role; // e.g., OWNER, MANAGER, STAFF
    @Column(columnDefinition = "BYTEA")
    private byte[] profileImageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDelete = false;

    @Column(unique = true, nullable = false)
    private String email;

    private String otp;

    private LocalDateTime otpExpiryTime;

    private Boolean otpVerified;
    
}
