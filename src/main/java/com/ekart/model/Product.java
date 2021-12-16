package com.ekart.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@TypeDefs({
		@TypeDef(
				name="list-array",
				typeClass = ListArrayType.class
		),
		@TypeDef(
				name="json",
				typeClass = JsonType.class
		)
})
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;

    private String name;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> description;

    private BigDecimal price;

    private Double discount;

    private Integer quantity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "size_id")
    @JsonIgnoreProperties("products")
    private Set<Size> size;

    private Color color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_category_id")
    @JsonIgnoreProperties("products")
    private SubCategory subCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    @JsonIgnoreProperties("products")
    private Brand brand;

    private Integer likes;

    private Integer dislikes;

    private Double rating;
    
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Map<String, List<String>> info;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_date")
    private LocalDateTime lastModifiedDate;

    @Lob
    @Column(name = "thumbnail_image")
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] thumbnailImage;

    @Lob
    @ElementCollection
    @Type(type = "org.hibernate.type.ImageType")
    @JsonIgnore
    private List<byte[]> images;

    public Product(String name, Category category, SubCategory subCategory, Brand brand, Set<Size> size, Color color, List<String> description,
                   BigDecimal price, Double discount, Integer quantity, byte[] thumbnailImage, List<byte[]> images
                   ) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.thumbnailImage = thumbnailImage;
        this.images = images;
    }
}
