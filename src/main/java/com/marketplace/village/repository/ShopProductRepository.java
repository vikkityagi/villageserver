package com.marketplace.village.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marketplace.village.entity.ShopProduct;

import java.util.List;
import java.util.UUID;

public interface ShopProductRepository extends JpaRepository<ShopProduct, UUID> {

    List<ShopProduct> findByShopId(UUID shopId);

    List<ShopProduct> findByIsDeleteFalseAndIsActiveTrue();

    @Query(value = "SELECT * FROM shop_product sp " +
            "WHERE sp.is_delete = false " +
            "AND sp.is_active = true " +
            "AND sp.shop_id = :shopId", nativeQuery = true)
    List<ShopProduct> findActiveProductsByShopId(@Param("shopId") UUID shopId);

    @Query(value = """
            SELECT *
            FROM shop_product sp
            WHERE sp.is_delete = false
              AND sp.is_active = true
              AND sp.product_name ILIKE CONCAT('%', :name, '%')
            """, nativeQuery = true)
    List<ShopProduct> findByProductNameContainingIgnoreCase(@Param("name") String name);

    @Query(value = "SELECT * FROM shop_product sp " +
            "WHERE sp.is_delete = false " +
            "AND sp.is_active = true " +
            "AND LOWER(sp.product_name) = LOWER(:name) " +
            "ORDER BY sp.product_price ASC " +
            "LIMIT 1", nativeQuery = true)
    ShopProduct findLowestPriceProductByName(@Param("name") String name);
}
