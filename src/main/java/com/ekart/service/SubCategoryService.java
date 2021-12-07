package com.ekart.service;

import com.ekart.dao.ProductRepository;
import com.ekart.dao.SubCategoryRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Product;
import com.ekart.model.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<SubCategory> getAllSubCategory() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        return subCategories.size() > 0 ? subCategories : new ArrayList<SubCategory>();
    }

    public SubCategory getSubCategoryById(Long id) throws RecordNotFoundException {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if (subCategory.isPresent()) {
            return subCategory.get();
        } else {
            throw new RecordNotFoundException("No category exists for given id " + id);
        }
    }
    public List<Product> findProductBySubCategory(SubCategory subCategory, Integer pageNo, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Optional<Page<Product>> productPage = productRepository.findProductBySubCategory(pageable,subCategory);
        if(productPage.isPresent()) {
            return productPage.get().getContent();
        } else {
            return new ArrayList<Product>();
        }

    }
}
