package com.ekart.dao;

import com.ekart.model.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "productBrand", path = "product-brand")
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {
}
