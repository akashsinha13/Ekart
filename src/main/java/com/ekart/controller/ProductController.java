package com.ekart.controller;

import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Product;
import com.ekart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(defaultValue = "name") String sortBy) {
        List<Product> products = productService.getAllProducts(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<Product>>(products, new HttpHeaders(), HttpStatus.OK);
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
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<Product>(newProduct, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteProductById(@PathVariable Long id) throws RecordNotFoundException {
        productService.deleteProductById(id);
        return HttpStatus.FORBIDDEN;
    }
}
