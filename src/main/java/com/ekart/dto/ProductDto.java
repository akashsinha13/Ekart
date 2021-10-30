package com.ekart.dto;

import com.ekart.model.Category;
import com.ekart.model.Color;
import com.ekart.model.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Double discount;
    private byte[] thumbnailImage;
    private Double rating;
    private Color color;
    private List<String> description;
    private Set<SizeDto> size;
}
