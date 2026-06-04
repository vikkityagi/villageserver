package com.marketplace.village.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketplace.village.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByIsDeletedFalseAndIsActiveTrue();

    @Query("SELECT c FROM Category c WHERE c.shopUser.id = :userId AND isActive = true AND isDeleted = false")
    List<Category> findCategoriesByUserId(@Param("userId") UUID userId);

    @Query("SELECT c FROM Category c WHERE c.isDeleted = false AND c.shopUser.id = :userId")
    List<Category> getActiveCategories(@Param("userId") UUID userId);

}
