package com.ekart.service;

import com.ekart.dao.ProductRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return products.size() > 0 ? products : new ArrayList<Product>();
    }

    public Product getProductById(Long id) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RecordNotFoundException("No product exists for given id " + id);
        }
    }

    public Product saveProduct(Product product) {
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
