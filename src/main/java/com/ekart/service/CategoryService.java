package com.ekart.service;

import com.ekart.dao.CategoryRepository;
import com.ekart.dao.ProductRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Category;
import com.ekart.model.Product;
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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.size() > 0 ? categories : new ArrayList<Category>();
    }

    public List<Product> findProductsByCategory(Category category, Integer pageNo, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Optional<Page<Product>> productPage = productRepository.findProductByCategory(pageable,category);
        if(productPage.isPresent()) {
            return productPage.get().getContent();
        } else {
            return new ArrayList<Product>();
        }

    }

    public Category getCategoryById(Long id) throws RecordNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new RecordNotFoundException("No category exists for given id " + id);
        }
    }
}
