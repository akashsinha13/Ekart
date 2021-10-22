package com.ekart.service;

import com.ekart.dao.BrandRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrand() {
        List<Brand> brands = brandRepository.findAll();
        return brands.size() > 0 ? brands : new ArrayList<Brand>();
    }

    public Brand getBrandById(Long id) throws RecordNotFoundException {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isPresent()) {
            return brand.get();
        } else {
            throw new RecordNotFoundException("No brand exists for given id " + id);
        }
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrandById(Long id) throws RecordNotFoundException {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isPresent()) {
            brandRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No brand exists for given id " + id);
        }
    }
}
