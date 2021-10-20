package com.ekart.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private List<String> description;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="discount")
    private Double discount;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="size")
    private ProductSize size;

    @Column(name="color")
    private ProductColor color;

    @Column(name="category")
    private ProductCategory category;

    @Column(name="brand")
    private ProductBrand brand;

    @Column(name="like")
    private Integer likes;

    @Column(name="dislike")
    private Integer dislikes;

    @Column(name="rating")
    private Double rating;

    @Column(name="create_date")
    @CreationTimestamp
    private LocalDate createdDate;

    @Column(name="modified_date")
    @UpdateTimestamp
    private LocalDate lastModifiedDate;

    @Column(name="info")
    private Map<String, List<String>> info;

    @Column(name="thumbnail_image")
    private byte[] thumbnailImage;

    @Column(name="images")
    private List<Byte[]> images;
}
