package com.marketplace.village.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.village.dto.ShopDto;
import com.marketplace.village.dto.ShopProductDto;
import com.marketplace.village.dto.ShopProductRequestDto;
import com.marketplace.village.dto.ShopUserDto;
import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopUser;

public interface ShopService {

    // manage shop

    ShopDto registerShop(ShopDto shopDto) throws Exception;

    ShopDto updateShop(UUID id,ShopDto shopDto) throws Exception;

    List<ShopDto> getActiveShop() throws Exception;

    List<ShopDto> getAllShops() throws Exception;

    List<ShopDto> getAllShopsByShopUserIdAndCategoryId(UUID shopId,UUID categoryId) throws Exception;
    
    List<ShopDto> activeShopUsingCategoryId(UUID categoryId);

    ShopDto getShopById(UUID id) throws Exception;

    void deleteShop(UUID id) throws Exception;

    // manage shop user

    ShopUserDto addShopUser(ShopUserDto shopUser) throws Exception;

    ShopUserDto loginShopUser(ShopUserDto shopUser) throws Exception;

    ShopUserDto forgotInfo(ShopUserDto shopUser) throws Exception;

    ShopUserDto getShopUserById(UUID id) throws Exception;

    ShopUserDto updateShopUser(UUID id,ShopUserDto shopUserDto) throws Exception;
    
    List<ShopUserDto> getActiveShopUser() throws Exception;

    void deleteShopUser(UUID id) throws Exception;

    List<ShopUserDto> findShopUserByContact(String mobileno) throws Exception;

    List<ShopUserDto> findShopUserByPinCode(String pinCode) throws Exception;

    List<ShopUserDto> findShopUserByPinCodeForTool(String pinCode) throws Exception;

    

    // manage shopproduct

    ShopProductRequestDto addShopProducts(ShopProductRequestDto shopProductRequestDto) throws Exception;

    ShopProductDto editShopProduct(UUID id,ShopProductDto shopProductDto) throws Exception;

    ShopProductDto getShopProductById(UUID id) throws Exception;
    

    List<ShopProductDto> getAllActiveShopProduct() throws Exception;

    void deleteShopProduct(UUID id) throws Exception;




}