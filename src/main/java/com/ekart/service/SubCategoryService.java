package com.ekart.service;

import com.ekart.dao.SubCategoryRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

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

    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public void deleteSubCategoryById(Long id) throws RecordNotFoundException {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if (subCategory.isPresent()) {
            subCategoryRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No sub category exists for given id " + id);
        }
    }
}
