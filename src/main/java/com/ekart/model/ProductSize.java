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
public class ProductSize {
    private Long id;

    private String size;

    private Set<Product> products;
}