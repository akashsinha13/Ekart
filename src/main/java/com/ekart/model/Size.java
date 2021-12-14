package com.ekart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
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
    @JsonIgnore
    private Set<SubCategory> subCategories;
}
