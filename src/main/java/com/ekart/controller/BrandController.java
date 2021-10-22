package com.ekart.controller;

import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Brand;
import com.ekart.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrand() {
        List<Brand> brands = brandService.getAllBrand();
        return new ResponseEntity<List<Brand>>(brands, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) throws RecordNotFoundException {
        Brand brand = brandService.getBrandById(id);
        return new ResponseEntity<Brand>(brand, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand) {
        Brand newBrand = brandService.saveBrand(brand);
        return new ResponseEntity<Brand>(newBrand, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteBrandById(@PathVariable Long id) throws RecordNotFoundException {
        brandService.deleteBrandById(id);
        return HttpStatus.FORBIDDEN;
    }
}
