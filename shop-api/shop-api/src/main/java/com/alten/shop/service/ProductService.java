package com.alten.shop.service;

import com.alten.shop.domain.dto.ProductDTO;
import com.alten.shop.domain.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    Product createProduct(ProductDTO product);
    Product updateProduct(ProductDTO product);
    ProductDTO getProductById(Long id);
    void deleteProduct(Long id);
    Page<ProductDTO> getProducts(int page, int size);
}
