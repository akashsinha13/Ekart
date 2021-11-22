package com.ekart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class SubCategory {

    @Id
    @SequenceGenerator(
            name = "sub_category_sequence",
            sequenceName = "sub_category_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_category_sequence"
    )
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "size_id")
    @JsonIgnore
    private Size size;
}
