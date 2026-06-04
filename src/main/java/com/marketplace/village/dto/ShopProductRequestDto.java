package com.marketplace.village.dto;



import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ShopProductRequestDto {

    private UUID shopId;
    private List<ShopProductDto> products;

}
