package com.marketplace.village.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.village.dto.ShopProductDto;
// import com.marketplace.village.entity.Product;
import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopProduct;
import com.marketplace.village.exception.CustomException;
// import com.marketplace.village.repository.ProductRepository;
import com.marketplace.village.repository.ShopProductRepository;
import com.marketplace.village.repository.ShopRepository;
import com.marketplace.village.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ShopProductRepository shopProductRepository;

    @Autowired
    private ShopRepository shopRepository;

    // @Override
    // public List<Product> searchProduct(String keyword) {
    // return productRepo.findByNameContainingIgnoreCase(keyword);
    // }

    // @Override
    // public List<ShopProduct> getProductsByShop(UUID shopId) {
    // return spRepo.findByShopId(shopId);
    // }

    @Override
    public List<ShopProductDto> addShopProduct(List<ShopProductDto> products) throws Exception {
        List<ShopProduct> shopProducts = new ArrayList<>();
        List<ShopProductDto> shopProductDtos = new ArrayList<>();
        for (ShopProductDto shopProductDto : products) {

            Optional<Shop> shop = shopRepository.findById(shopProductDto.getShopId());
            if (!shop.isPresent()) {
                throw new CustomException("Shop not found");
            }

            if (!shop.get().getIsActive()) {
                throw new CustomException("Shop not Active");
            }

            ShopProduct shopProduct = new ShopProduct();

            // ShopProduct product = new ShopProduct();
            shopProduct.setProductName(shopProductDto.getProductName());
            shopProduct.setProductPrice(shopProductDto.getProductPrice());
            shopProduct.setProductQuantity(shopProductDto.getProductQuantity());
            shopProduct.setIsActive(shopProductDto.getIsActive());
            shopProduct.setCreatedAt(LocalDateTime.now());
            shopProduct.setProductDescription(shopProductDto.getProductDescription());
            if (shopProductDto.getProductPinCode().equals(shop.get().getPinCode())) {
                shopProduct.setProductPinCode(shop.get().getShopUser().getPinCode());
            } else {
                throw new CustomException("Product pin code must match shop pin code");
            }
            shopProduct.setProductPinCode(shopProductDto.getProductPinCode());
            shopProduct.setProductUnit(shopProductDto.getProductUnit());
            shopProduct.setShop(shop.get());

            shopProducts.add(shopProduct);

        }

        shopProducts = this.shopProductRepository.saveAll(shopProducts);

        for (ShopProduct shopProduct : shopProducts) {
            ShopProductDto shopProductDto = new ShopProductDto();

            shopProductDto.setId(shopProduct.getId());
            shopProductDto.setProductName(shopProduct.getProductName());
            shopProductDto.setProductPrice(shopProduct.getProductPrice());
            shopProductDto.setProductQuantity(shopProduct.getProductQuantity());
            shopProductDto.setShopId(shopProduct.getShop().getId());
            shopProductDto.setIsActive(shopProduct.getIsActive());
            shopProductDto.setProductPinCode(shopProduct.getProductPinCode());
            shopProductDto.setProductUnit(shopProduct.getProductUnit());
            // shopProductDto.setProductQuantity(shopProduct.getProductQuantity());
            shopProductDto.setProductUnit(shopProduct.getProductUnit());

            shopProductDtos.add(shopProductDto);
        }

        return shopProductDtos;
    }

    @Override
    public List<ShopProductDto> getShopProductsUsingShopId(UUID shopId) throws Exception {
        List<ShopProductDto> shopProductDtos = new ArrayList<>();
        List<ShopProduct> shopProducts = shopProductRepository.findActiveProductsByShopId(shopId);
        for (ShopProduct shopProduct : shopProducts) {
            ShopProductDto shopProductDto = new ShopProductDto();

            shopProductDto.setId(shopProduct.getId());
            shopProductDto.setProductName(shopProduct.getProductName());
            shopProductDto.setProductPrice(shopProduct.getProductPrice());
            shopProductDto.setProductQuantity(shopProduct.getProductQuantity());
            shopProductDto.setShopId(shopProduct.getShop().getId());
            shopProductDto.setIsActive(shopProduct.getIsActive());
            shopProductDto.setProductPinCode(shopProduct.getProductPinCode());
            shopProductDto.setProductDescription(shopProduct.getProductDescription());
            shopProductDto.setProductUnit(shopProduct.getProductUnit());

            shopProductDtos.add(shopProductDto);
        }
        return shopProductDtos;
    }

    @Override
    public ShopProductDto updateShopProduct(UUID id, ShopProductDto shopProductDto) throws Exception {
        Optional<ShopProduct> shopProductOpt = shopProductRepository.findById(id);
        if (!shopProductOpt.isPresent()) {
            throw new CustomException("Shop Product not found");
        }

        // Optional<Shop> shop = shopRepository.findById(shopProductDto.getShopId());
        // if (!shop.isPresent()) {
        //     throw new CustomException("Shop not found");
        // }

        ShopProduct shopProduct = shopProductOpt.get();

        shopProduct.setProductName(shopProductDto.getProductName());
        shopProduct.setProductPrice(shopProductDto.getProductPrice());
        shopProduct.setProductQuantity(shopProductDto.getProductQuantity());
        shopProduct.setIsActive(shopProductDto.getIsActive());
        shopProduct.setUpdatedAt(LocalDateTime.now());
        shopProduct.setProductDescription(shopProductDto.getProductDescription());

        if(shopProductDto.getProductPinCode().equals(shopProduct.getProductPinCode())) {
            shopProduct.setProductPinCode(shopProduct.getProductPinCode());
        } else {
            throw new CustomException("Product pin code must match shop pin code");
        }
        // shopProduct.setProductPinCode(shopProductDto.getProductPinCode());
        shopProduct.setProductUnit(shopProductDto.getProductUnit());

        shopProduct = shopProductRepository.save(shopProduct);

        ShopProductDto updatedShopProductDto = new ShopProductDto();
        updatedShopProductDto.setId(shopProduct.getId());
        updatedShopProductDto.setProductName(shopProduct.getProductName());
        updatedShopProductDto.setProductPrice(shopProduct.getProductPrice());
        updatedShopProductDto.setShopId(shopProduct.getShop().getId());
        updatedShopProductDto.setIsActive(shopProduct.getIsActive());
        // updatedShopProductDto.setUpdatedAt(shopProduct.getUpdatedAt());
        // updatedShopProductDto.setCreatedAt(shopProduct.getCreatedAt());
        // updatedShopProductDto.setUpdatedAt(LocalDateTime.now());
        // updatedShopProductDto.setIsActive(shopProduct.getIsActive());
        // updatedShopProductDto.setUpdatedAt(LocalDateTime.now());

        return updatedShopProductDto;
    }

    @Override
    public List<ShopProductDto> findProductByName(String name) throws Exception {
        List<ShopProductDto> shopProductDtos = new ArrayList<>();
        List<ShopProduct> shopProducts = shopProductRepository.findByProductNameContainingIgnoreCase(name);
        for (ShopProduct shopProduct : shopProducts) {
            ShopProductDto shopProductDto = new ShopProductDto();

            shopProductDto.setId(shopProduct.getId());
            shopProductDto.setProductName(shopProduct.getProductName());
            shopProductDto.setProductPrice(shopProduct.getProductPrice());
            shopProductDto.setProductQuantity(shopProduct.getProductQuantity());
            shopProductDto.setShopId(shopProduct.getShop().getId());
            shopProductDto.setIsActive(shopProduct.getIsActive());
            shopProductDto.setProductPinCode(shopProduct.getProductPinCode());
            shopProductDto.setProductDescription(shopProduct.getProductDescription());
            shopProductDto.setProductUnit(shopProduct.getProductUnit());
            shopProductDto.setShopAddress(shopProduct.getShop().getShopAddress());
            shopProductDto.setShopContact(shopProduct.getShop().getContact());
            shopProductDto.setShopUserAddress(shopProduct.getShop().getShopUser().getShopAddress());
            shopProductDto.setShopUserPinCode(shopProduct.getShop().getShopUser().getPinCode());

            shopProductDtos.add(shopProductDto);
        }
        return shopProductDtos;
    }

    @Override
    public ShopProductDto findLowestPriceProduct(String name) throws Exception {
        ShopProduct shopProduct = shopProductRepository.findLowestPriceProductByName(name);
        if (shopProduct == null) {
            throw new CustomException("No product found with the given name");
        }

        ShopProductDto shopProductDto = new ShopProductDto();
        shopProductDto.setId(shopProduct.getId());
        shopProductDto.setProductName(shopProduct.getProductName());
        shopProductDto.setProductPrice(shopProduct.getProductPrice());
        shopProductDto.setShopId(shopProduct.getShop().getId());
        shopProductDto.setIsActive(shopProduct.getIsActive());
        shopProductDto.setProductPinCode(shopProduct.getProductPinCode());
        shopProductDto.setProductDescription(shopProduct.getProductDescription());
        shopProductDto.setProductUnit(shopProduct.getProductUnit());
        shopProductDto.setShopAddress(shopProduct.getShop().getShopAddress());
        shopProductDto.setShopContact(shopProduct.getShop().getContact());
        shopProductDto.setShopUserAddress(shopProduct.getShop().getShopUser().getShopAddress());
        shopProductDto.setShopUserPinCode(shopProduct.getShop().getShopUser().getPinCode());

        return shopProductDto;
    }

}