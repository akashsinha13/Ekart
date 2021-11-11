package com.ekart.service;

import com.ekart.constants.Constants;
import com.ekart.dao.BrandRepository;
import com.ekart.dao.CategoryRepository;
import com.ekart.dao.ProductRepository;
import com.ekart.dao.SizeRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SizeRepository sizeRepository;

    public List<Product> getAllProducts(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Product> productPage = productRepository.findAll(pageable);
        if(productPage.hasContent()) {
            return productPage.getContent();
        } else {
            return new ArrayList<Product>();
        }
    }

    public List<byte[]> getProductImagesById(Long id) {
        List<byte[]> images = productRepository.getProductImagesById(id);
        return images.size() > 0 ? images : new ArrayList<byte[]>();
    }

    public Product getProductById(Long id) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RecordNotFoundException("No product exists for given id " + id);
        }
    }

    public Product saveProduct(Map<String, Object> request) {
        Category newCategory;
        Brand newBrand;
        Set<Size> newSize = new HashSet<>();
        List<String> desc = new ArrayList<>();

        String categoryName = (String) request.get(Constants.CATEGORY);
        Optional<Category> category = categoryRepository.findCategoryByName(categoryName);
        if(!category.isPresent()) {
            newCategory = new Category();
            newCategory.setName(categoryName);
        } else {
            newCategory = category.get();
        }
        String brandName = (String) request.get(Constants.BRAND);
        Optional<Brand> brand = brandRepository.findBrandByName(brandName);
        if(!brand.isPresent()) {
            newBrand = new Brand();
            newBrand.setName(brandName);
        } else {
            newBrand = brand.get();
        }
        String sizeName = (String) request.get(Constants.SIZE);
        if(sizeName != null && sizeName.length() > 0) {
            Optional<Size> size = sizeRepository.findSizeBySize(sizeName);
            if(!size.isPresent()) {
                Size sizeAdd = new Size();
                sizeAdd.setSize(sizeName);
                newSize.add(sizeAdd);
            } else {
                newSize.add(size.get());
            }
        }
        String description = (String) request.get(Constants.DESCRIPTION);
        if(description != null) {
            desc = Arrays.stream(description.split("\n")).collect(Collectors.toList());
        }
        String color = (String) request.get(Constants.COLOR);
        Color newColor = Color.valueOf(color);
        byte[] thumbnailImage = ((String) request.get(Constants.THUMBNAIL)).getBytes();
        List<byte[]> images = ((List<String>) request.get(Constants.IMAGES))
                                .stream().map(data -> data.getBytes()).collect(Collectors.toList());
        String name = (String) request.get(Constants.NAME);
        BigDecimal price = new BigDecimal((Integer) request.get(Constants.PRICE));
        Integer quantity = (Integer) request.get(Constants.QUANTITY);
        Double discount = ((Integer) request.get(Constants.DISCOUNT)).doubleValue();

        Product product = new Product(name, newCategory, newBrand, newSize, newColor, desc,
                price, discount, quantity, thumbnailImage,  images);
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No product exists for given id " + id);
        }
    }
}
