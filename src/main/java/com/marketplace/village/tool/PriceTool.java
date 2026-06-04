package com.marketplace.village.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.marketplace.village.dto.ShopProductDto;
import com.marketplace.village.service.ProductService;

@Component
public class PriceTool {

    private final ProductService productService;

    public PriceTool(ProductService productService) {
        this.productService = productService;
    }

    @Tool(description = """
            Find the lowest price product by product name.

            Always return the response in the following format:

            Product Details:
            - Product Name
            - Product Price
            - Product Unit
            - Product Description

            Shop Details:
            - Shop Address
            - Shop Contact
            - Shop User Address
            - Shop User Pin Code

            If any field is null, display 'Not Available'.
            """)
    public ShopProductDto findLowestPriceProduct(String productName) throws Exception {
        ShopProductDto dto = productService.findLowestPriceProduct(productName);

        System.out.println(dto);
        return dto;

    }
}