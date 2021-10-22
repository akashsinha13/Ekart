package com.ekart.service;

import com.ekart.dao.CategoryRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {

        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) throws RecordNotFoundException {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            return category;
        } else {
            throw new RecordNotFoundException("No category exists for given id " + id);
        }
    }

    public Category saveCategory(Category category) {

        return categoryRepository.save(category);
    }


    public void deleteCategoryById(Long id) throws RecordNotFoundException {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No category exists for given id " + id);
        }
    }
}
