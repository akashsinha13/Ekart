package com.ekart.model;

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

    private String size;

    private Set<Product> products;
}