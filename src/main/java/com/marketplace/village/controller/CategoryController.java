package com.marketplace.village.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.village.dto.CategoryDto;
import com.marketplace.village.dto.ShopDto;
import com.marketplace.village.service.CategoryService;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    //test
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        try{
            categoryDto = categoryService.addCategory(categoryDto);
            return new ResponseEntity<>(categoryDto,HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // this
    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable UUID id,@RequestBody CategoryDto categoryDto) {
        try{
            categoryDto = categoryService.updateCategory(id, categoryDto);
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/categories/for_shop/{shop_user_id}")
    public ResponseEntity<List<CategoryDto>> getAllCategory(@PathVariable UUID shop_user_id) {
        try{
            List<CategoryDto> categoryDtos = new ArrayList<>();
            categoryDtos = categoryService.findAllCategoryByShopUserId(shop_user_id);
            return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/categories/for_customer/{shopUserId}")
    public ResponseEntity<List<CategoryDto>> getAllCategoryByShopUserId(@PathVariable UUID shopUserId) throws Exception {
        
            List<CategoryDto> categoryDtos = new ArrayList<>();
            categoryDtos = categoryService.findAllActiveCategoryByShopUser(shopUserId);
            return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
        
    }

    @GetMapping("/active/categories")
    public ResponseEntity<List<CategoryDto>> activeCategory() {
        try{
            List<CategoryDto> categoryDtos = new ArrayList<>();
            categoryDtos = categoryService.findAllActiveCategory();
            return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable UUID id) {
        try{
            CategoryDto categoryDto = new CategoryDto();
            categoryDto = categoryService.getById(id);
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable UUID id) {
        try{
            
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/categories/:status/{categoryId}")
    public ResponseEntity<CategoryDto> changeStatus(@PathVariable UUID categoryId) {
        try{
            CategoryDto categoryDto = new CategoryDto();
            categoryDto = categoryService.changeStatus(categoryId);
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
