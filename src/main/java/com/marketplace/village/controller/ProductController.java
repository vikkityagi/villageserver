package com.marketplace.village.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.village.dto.ShopProductDto;

import com.marketplace.village.entity.ShopProduct;
import com.marketplace.village.service.ProductService;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/products")
    public ResponseEntity<List<ShopProductDto>> addProduct(@RequestBody List<ShopProductDto> productDtos) throws Exception {
        try {
            productDtos = service.addShopProduct(productDtos);
            return new ResponseEntity<>(productDtos,HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
        
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ShopProductDto> updateProduct(@PathVariable UUID id,@RequestBody ShopProductDto productDto) throws Exception {
        try {
            productDto = service.updateShopProduct(id, productDto);
            return new ResponseEntity<>(productDto,HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
        
    }   

    // @GetMapping("/search")
    // public List<Product> search(@RequestParam String keyword) {
    // return service.searchProduct(keyword);
    // }

    // @GetMapping("/shop/{shopId}")
    // public List<ShopProduct> getByShop(@PathVariable UUID shopId) {
    // return service.getProductsByShop(shopId);
    // }

    @GetMapping("/products/{shopId}")
    public ResponseEntity<List<ShopProductDto>> getAll(@PathVariable UUID shopId) {
        List<ShopProductDto> shopProductDtos = new ArrayList<>();
        try{
            shopProductDtos = service.getShopProductsUsingShopId(shopId);
            return new ResponseEntity<>(shopProductDtos,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(shopProductDtos,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}