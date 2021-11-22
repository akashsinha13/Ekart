package com.ekart.controller;

import com.ekart.dto.ProductDto;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Product;
import com.ekart.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${apiPrefix}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                           @RequestParam(defaultValue = "name") String sortBy) {
        List<Product> products = productService.getAllProducts(pageNo, pageSize, sortBy);
        List<ProductDto> productsResponse = products.stream()
                                                    .map(product -> modelMapper.map(product, ProductDto.class))
                                                    .collect(Collectors.toList());
        return new ResponseEntity<List<ProductDto>>(productsResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/images")
    public ResponseEntity<List<byte[]>> getProductImagesById(@PathVariable Long id) {
        List<byte[]> products = productService.getProductImagesById(id);
        return new ResponseEntity<List<byte[]>>(products, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws RecordNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<Product>(product, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Map<String, Object> request) {
        Product newProduct = productService.saveProduct(request);
        return new ResponseEntity<Product>(newProduct, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteProductById(@PathVariable Long id) throws RecordNotFoundException {
        productService.deleteProductById(id);
        return HttpStatus.FORBIDDEN;
    }
}
