package com.ekart.model;

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
public class Brand {

    @Id
    @SequenceGenerator(
            name = "brand_sequence",
            sequenceName = "brand_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "brand_sequence"
    )
    private Long id;

    @Column(unique = true)
    private String name;
    

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("brand")
    private Set<Product> products;
}
