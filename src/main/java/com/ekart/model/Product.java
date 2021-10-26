package com.ekart.model;

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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "size_id")
    private Size size;

    private Color color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private Integer likes;

    private Integer dislikes;

    private Double rating;
    
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Map<String, List<String>> info;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDate lastModifiedDate;

    @Lob
    @Column(name = "thumbnail_image")
    private byte[] thumbnailImage;

    @Lob
    @ElementCollection(fetch = FetchType.EAGER)
    private List<byte[]> images;
}
