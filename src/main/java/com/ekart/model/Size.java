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
public class Size {

    @Id
    @SequenceGenerator(
            name = "size_sequence",
            sequenceName = "size_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "size_sequence"
    )
    private Long id;

    @Column(unique = true)
    private String size;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("size")
    private Set<Product> products;
}
