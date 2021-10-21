package com.ekart.service;

import com.ekart.dao.BrandRepository;
import com.ekart.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;
}
