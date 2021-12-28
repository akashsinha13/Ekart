package com.ekart.dto;

import lombok.Data;

@Data
public class SubCategoryDto {
    private Long id;
    private String name;
    private Long categoryId;
    private int totalCount;
}
