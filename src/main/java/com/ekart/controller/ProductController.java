package com.ekart.controller;

import com.ekart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/products")
public class ProductController {

    @Autowired
    private ProductService productService;
}
