package com.ekart.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Product {
    private Long id;

    private String name;

    private List<String> description;

    private BigDecimal price;

    private Double discount;

    private Integer quantity;

    private ProductSize size;

    private ProductColor color;

    private ProductType type;

    private ProductBrand brand;

    private Integer likes;

    private Integer dislikes;

    private Double rating;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    private Map<String, List<String>> info;

    private byte[] thumbnailImage;

    private List<Byte[]> images;
}
