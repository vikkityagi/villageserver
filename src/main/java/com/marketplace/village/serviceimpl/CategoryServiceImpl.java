package com.marketplace.village.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.village.dto.CategoryDto;

import com.marketplace.village.entity.Category;
import com.marketplace.village.entity.ShopUser;
import com.marketplace.village.exception.CustomException;
import com.marketplace.village.repository.CategoryRepository;
import com.marketplace.village.repository.ShopUserRepository;
import com.marketplace.village.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ShopUserRepository shopUserRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) throws Exception {
        // TODO Auto-generated method stub
        Category category = new Category();

        category = convertDtoToEntity(categoryDto);

        category = this.categoryRepository.save(category);

        categoryDto = convertEntityToDto(category);

        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(UUID id, CategoryDto categoryDto) throws Exception {
        // TODO Auto-generated method stub
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new CustomException("Category not found");
        }

        Category category = categoryOptional.get();

        
        

        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDesc(categoryDto.getCategoryDesc());
        category.setCreatedAt(category.getCreatedAt());
        category.setUpdatedAt(LocalDateTime.now());
        category.setIsActive(categoryDto.getIsActive());

        Optional<ShopUser> shopUserOpt = this.shopUserRepository.findById(categoryDto.getShopUser());
        if (!shopUserOpt.isPresent()) {
            throw new CustomException("ShopUser not found");
        }

        category.setShopUser(shopUserOpt.get());

        category = this.categoryRepository.save(category);

        categoryDto = convertEntityToDto(category);

        return categoryDto;
    }

    @Override
    public CategoryDto getById(UUID id) throws Exception {
        // TODO Auto-generated method stub
        CategoryDto categoryDto = new CategoryDto();
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new CustomException("Category not found");
        }

        Category category = categoryOptional.get();

        categoryDto = convertEntityToDto(category);

        return categoryDto;

    }

    @Override
    public List<CategoryDto> findAllActiveCategory() throws Exception {
        // TODO Auto-generated method stub
        List<Category> categories = this.categoryRepository.findByIsDeletedFalseAndIsActiveTrue();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto = convertEntityToDto(category);
            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }

    @Override
    public List<CategoryDto> findAllCategoryByShopUserId(UUID shopUserId) {
        // TODO Auto-generated method stub
        List<Category> categories = this.categoryRepository.getActiveCategories(shopUserId);
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto = convertEntityToDto(category);
            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }

    @Override
    public void deleteCategory(UUID id) throws Exception {
        // TODO Auto-generated method stub
        CategoryDto categoryDto = new CategoryDto();
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new CustomException("Category not found");
        }

        Category category = categoryOptional.get();
        category.setIsDeleted(false);
        category = categoryRepository.save(category);

    }

    private Category convertDtoToEntity(CategoryDto categoryDto) throws Exception {

        Category category = new Category();
        

        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDesc(categoryDto.getCategoryDesc());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setIsActive(categoryDto.getIsActive());

        Optional<ShopUser> shopUserOpt = this.shopUserRepository.findById(categoryDto.getShopUser());
        if (!shopUserOpt.isPresent()) {
            throw new CustomException("ShopUser not found");
        }

        category.setShopUser(shopUserOpt.get());

        return category;

    }

    private CategoryDto convertEntityToDto(Category category) {

        // System.out.println("Category Entity: " + category.toString());

        CategoryDto categoryDto = new CategoryDto();
        List<UUID> shopUsersDto = new ArrayList<>();

        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setCategoryDesc(category.getCategoryDesc());
        categoryDto.setCreatedAt(category.getCreatedAt());
        categoryDto.setUpdatedAt(category.getUpdatedAt());
        categoryDto.setIsActive(category.getIsActive());

        // for (UUID id : category.getShopUser().getId()) {

        // Optional<ShopUser> shopUserOpt =
        // this.shopUserRepository.findById(shopUser.getId());
        // if (!shopUserOpt.isPresent()) {
        // throw new CustomException("ShopUser not found");
        // }

        // shopUsersDto.add(shopUserOpt.get().getId());

        // }
        // shopUsersDto.add(category.getShopUser().getId());
        categoryDto.setShopUser(category.getShopUser().getId());

        return categoryDto;

    }

    @Override
    public List<CategoryDto> findAllActiveCategoryByShopUser(UUID shopUser) throws Exception {
        // TODO Auto-generated method stub

        List<Category> categories = new ArrayList<>();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        Optional<ShopUser> shopUserOptional = this.shopUserRepository.findById(shopUser);

        if (!shopUserOptional.isPresent()) {
            throw new CustomException("Not Shop found so we will not fetch the categories");
        }

        if (shopUserOptional.get().getIsActive()) {
            categories = this.categoryRepository.findCategoriesByUserId(shopUser);
            if (categories.size() <= 0) {
                throw new CustomException("No categories found");
            }

            for (Category category : categories) {
                CategoryDto categoryDto = new CategoryDto();
                categoryDto = convertEntityToDto(category);
                categoryDtos.add(categoryDto);
            }

        }else{
            throw new CustomException("Shop User not active so we will not fetch the categories");
        }

        return categoryDtos;

    }

    @Override
    public CategoryDto changeStatus(UUID id) throws Exception {
        // TODO Auto-generated method stub
        CategoryDto categoryDto = new CategoryDto();
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new CustomException("Category not found");
        }

        Category category = categoryOptional.get();
        category.setIsActive(!category.getIsActive());
        category = categoryRepository.save(category);

        categoryDto = convertEntityToDto(category);

        return categoryDto;
    }

}
