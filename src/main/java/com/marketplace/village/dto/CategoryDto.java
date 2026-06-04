package com.marketplace.village.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CategoryDto {

    private UUID id;

    private List<String> categoryName;

    private String categoryDesc;

    private Boolean isActive;

    private Boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UUID shopUser;
}