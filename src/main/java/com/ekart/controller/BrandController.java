package com.ekart.controller;

import com.ekart.dto.BrandDto;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Brand;
import com.ekart.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${apiPrefix}/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrand() {
        List<Brand> brands = brandService.getAllBrand();
        List<BrandDto> brandsResponse = brands.stream()
                                              .map(brand -> modelMapper.map(brand, BrandDto.class))
                                              .collect(Collectors.toList());
        return new ResponseEntity<List<BrandDto>>(brandsResponse, new HttpHeaders(), HttpStatus.OK);
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
