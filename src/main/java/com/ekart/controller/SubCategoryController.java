package com.ekart.controller;

import com.ekart.dto.SubCategoryDto;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.SubCategory;
import com.ekart.service.SubCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${apiPrefix}/subcategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<SubCategoryDto>> getAllSubCategory() {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategory();
        List<SubCategoryDto> subCategoryResponse = subCategories.stream()
                                                       .map(subCategory -> modelMapper.map(subCategory, SubCategoryDto.class))
                                                       .collect(Collectors.toList());
        return new ResponseEntity<List<SubCategoryDto>>(subCategoryResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long id) throws RecordNotFoundException {
        SubCategory subCategory = subCategoryService.getSubCategoryById(id);
        return new ResponseEntity<SubCategory>(subCategory, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubCategory> addSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory newSubCategory = subCategoryService.saveSubCategory(subCategory);
        return new ResponseEntity<SubCategory>(newSubCategory, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteSubCategoryById(@PathVariable Long id) throws RecordNotFoundException {
        subCategoryService.deleteSubCategoryById(id);
        return HttpStatus.FORBIDDEN;
    }
}
