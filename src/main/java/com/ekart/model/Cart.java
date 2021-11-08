package com.ekart.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @SequenceGenerator(
            name = "cart_sequence",
            sequenceName = "cart_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cart_sequence"
    )
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private List<Product> products;

    @Column(name = "sub_total")
    private BigDecimal subTotal;

    @Column(name = "item_count")
    private int itemCount;
}
