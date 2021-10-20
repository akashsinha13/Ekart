package com.ekart.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ProductBrand {
    private Long id;

    private String name;

    private Set<Product> products;
}
