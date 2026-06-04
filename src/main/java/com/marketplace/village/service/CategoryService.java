package com.marketplace.village.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.village.dto.CategoryDto;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto) throws Exception;

    CategoryDto updateCategory(UUID id,CategoryDto categoryDto) throws Exception;

    CategoryDto getById(UUID id) throws Exception;

    CategoryDto changeStatus(UUID id) throws Exception;

    List<CategoryDto> findAllCategoryByShopUserId(UUID shopUserId);

    List<CategoryDto> findAllActiveCategory() throws Exception;

    void deleteCategory(UUID id) throws Exception;

    List<CategoryDto> findAllActiveCategoryByShopUser(UUID shopUserId) throws Exception;


    
} 