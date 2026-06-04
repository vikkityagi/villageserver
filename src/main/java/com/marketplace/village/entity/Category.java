package com.marketplace.village.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;
import java.util.UUID;

@Entity
@Data
@ToString
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    @ElementCollection
    @CollectionTable(
        name = "category_names",
        joinColumns = @JoinColumn(name = "category_id")
    )
    @Column(name = "category_name")
    private List<String> categoryName;

    private String categoryDesc;

    private Boolean isActive = true;

    private Boolean isDeleted = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ✅ Many category → one user (OWNER)
    @ManyToOne
    @JoinColumn(name = "shop_user_id")
    private ShopUser shopUser;

    // ✅ One category → many shops
    @OneToMany(mappedBy = "category")
    private List<Shop> shops;

}
