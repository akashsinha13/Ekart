package com.ekart.dao;

import com.ekart.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "productSize", path = "product-size")
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
}
