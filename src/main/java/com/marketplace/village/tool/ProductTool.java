package com.marketplace.village.tool;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.marketplace.village.dto.ShopProductDto;
import com.marketplace.village.service.ProductService;

@Component
public class ProductTool {

    private final ProductService productService;

    public ProductTool(ProductService productService) {
        this.productService = productService;
    }

    @Tool(description = """
            Find all products matching the given product name.

            Return every matching product including:
            - product name
            - product price
            - product quantity
            - product unit
            - product pincode
            - shop user pincode
            - shop address
            - shop contact number
            - shop user address

            Do not return only one product.
            Always use all returned records when answering.
            """)
    public List<ShopProductDto> findProductByName(String productName) throws Exception {
        return productService.findProductByName(productName);
    }

}