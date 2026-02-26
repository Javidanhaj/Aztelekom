package com.aztelekom.internship.service.impl;

import com.aztelekom.internship.domain.entities.Category;
import com.aztelekom.internship.domain.entities.Product;
import com.aztelekom.internship.domain.enums.ProductStatus;
import com.aztelekom.internship.dto.product.ProductCreateRequest;
import com.aztelekom.internship.dto.product.ProductResponse;
import com.aztelekom.internship.dto.product.ProductUpdateRequest;
import com.aztelekom.internship.mapper.ProductMapper;
import com.aztelekom.internship.repository.CategoryRepository;
import com.aztelekom.internship.repository.ProductRepository;
import com.aztelekom.internship.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public Page<ProductResponse> getProducts(String search, UUID categoryId, ProductStatus status, Pageable pageable) {

        String q = (search == null) ? "" : search.trim();

        Page<Product> page;

        if (!q.isEmpty()) {
            page = productRepository.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(q, q, pageable);
        } else if (status == null && categoryId == null) {
            page = productRepository.findAll(pageable);
        } else if (status != null && categoryId == null) {
            page = productRepository.findByStatus(status, pageable);
        } else if (status == null) {
            page = productRepository.findByCategory_Id(categoryId, pageable);
        } else {
            page = productRepository.findByStatusAndCategory_Id(status, categoryId, pageable);
        }

        return page.map(productMapper::toResponse);
    }

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        if (productRepository.existsBySkuIgnoreCase(request.sku())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with sku " + request.sku() + " already exists.");
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + request.categoryId()));
        Product product = productMapper.createToEntity(request);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(UUID id, ProductUpdateRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        if (productRepository.existsBySkuIgnoreCase(request.sku()) && !existingProduct.getSku().equalsIgnoreCase(request.sku())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with sku " + request.sku() + " already exists.");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + request.categoryId()));
        existingProduct = productMapper.updateToEntity(request, existingProduct);
        existingProduct.setCategory(category);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
