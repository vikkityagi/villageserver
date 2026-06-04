package com.marketplace.village.tool;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.marketplace.village.dto.ShopDto;
import com.marketplace.village.dto.ShopUserDto;
import com.marketplace.village.service.ShopService;

@Component
public class ShopTools {

    private final ShopService shopService;

    public ShopTools(ShopService shopService) {
        this.shopService = shopService;
    }

    @Tool(description = """
            Find all shop users matching the given pincode.

            Return every matching shop user including:
            - name
            - pin code
            - shop address
            - contact number

            Do not return only one shop user.
            Always use all returned records when answering.
            Use the actual values returned by the tool.
            """)
    public List<ShopUserDto> getShopUserByPincode(String pincode) throws Exception {
        System.out.println("Received pincode: " + pincode);
        return shopService.findShopUserByPinCodeForTool(pincode);
    }

}