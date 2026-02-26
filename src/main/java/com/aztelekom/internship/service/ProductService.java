package com.aztelekom.internship.service;

import com.aztelekom.internship.domain.entities.Product;
import com.aztelekom.internship.dto.product.ProductCreateRequest;
import com.aztelekom.internship.dto.product.ProductResponse;
import com.aztelekom.internship.dto.product.ProductUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {


    Page<ProductResponse> getProducts();

    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse getProductById(UUID id);

    ProductResponse updateProduct(UUID id, ProductUpdateRequest request);

    void deleteProduct(UUID id);
}
