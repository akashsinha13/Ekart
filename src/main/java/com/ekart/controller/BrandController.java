package com.ekart.controller;

import com.ekart.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;
}
