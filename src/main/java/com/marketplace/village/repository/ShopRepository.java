package com.marketplace.village.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopUser;

public interface ShopRepository extends JpaRepository<Shop, UUID> {

    

    Shop findByContact(String contact);

    List<Shop> findByIsDeletedFalseAndIsActiveTrue();

    List<Shop> findByIsDeletedFalse();

    @Query(value="select *from shop where is_deleted = false and shop_user_id =:shopId and category_id =:categoryId",nativeQuery = true)
    List<Shop> findByIsDeletedFalseAndShopUserIdAndCategoryId(UUID shopId, UUID categoryId);

    @Query(value="select *from shop where is_deleted = false and is_active = true and category_id =:categoryId",nativeQuery = true)
    List<Shop> findByIsDeletedFalseAndIsActiveTrue(UUID categoryId);

    // List<ShopUser> findByIsDeleteFalseAndIsActiveTrue();
}
