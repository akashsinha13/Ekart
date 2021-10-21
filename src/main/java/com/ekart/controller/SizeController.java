package com.ekart.controller;

import com.ekart.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/sizes")
public class SizeController {

    @Autowired
    private SizeService sizeService;
}
