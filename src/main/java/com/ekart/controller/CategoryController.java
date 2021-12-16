package com.ekart.controller;

import com.ekart.dto.CategoryDto;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Category;
import com.ekart.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${apiPrefix}/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        List<CategoryDto> categoryResponse = categories.stream()
                                                       .map(category -> {
                                                           CategoryDto dto = modelMapper.map(category, CategoryDto.class);
                                                           dto.setTotalCount(category.getProducts().size());
                                                           return dto;
                                                       })
                                                       .collect(Collectors.toList());
        return new ResponseEntity<List<CategoryDto>>(categoryResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws RecordNotFoundException {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<Category>(category, new HttpHeaders(), HttpStatus.OK);
    }

}
