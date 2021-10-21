package com.ekart.controller;

import com.ekart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
}
