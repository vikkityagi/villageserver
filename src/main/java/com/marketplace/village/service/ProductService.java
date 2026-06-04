package com.marketplace.village.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.village.dto.ShopProductDto;

import com.marketplace.village.entity.ShopProduct;

public interface ProductService {

    // List<Product> searchProduct(String keyword);

    List<ShopProductDto> addShopProduct(List<ShopProductDto> products) throws Exception;

    // List<ShopProduct> getProductsByShop(UUID shopId);

    List<ShopProductDto> findProductByName(String name) throws Exception;

    ShopProductDto findLowestPriceProduct(String name) throws Exception;

    List<ShopProductDto> getShopProductsUsingShopId(UUID shopId) throws Exception;

    ShopProductDto updateShopProduct(UUID id,ShopProductDto shopProductDto) throws Exception;
}