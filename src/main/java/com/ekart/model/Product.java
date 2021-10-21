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
@Table
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

    private List<String> description;

    private BigDecimal price;

    private Double discount;

    private Integer quantity;

    private Size size;

    private Color color;

    private Category category;

    private Brand brand;

    private Integer likes;

    private Integer dislikes;

    private Double rating;

    private Map<String, List<String>> info;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDate lastModifiedDate;

    @Lob
    @Column(name = "thumbnail_image", columnDefinition = "BLOB")
    private byte[] thumbnailImage;

    @Lob
    @Column(columnDefinition = "BLOB")
    private List<Byte[]> images;
}
