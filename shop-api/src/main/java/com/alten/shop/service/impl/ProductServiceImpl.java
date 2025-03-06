package com.alten.shop.service.impl;

import com.alten.shop.domain.dto.ProductDTO;
import com.alten.shop.domain.mapper.ProductDTOMapper;
import com.alten.shop.domain.model.Product;
import com.alten.shop.exception.ApiException;
import com.alten.shop.repository.ProductRepository;
import com.alten.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product product = ProductDTOMapper.toProduct(productDTO);
        return  productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) {
        if (!productRepository.existsById(productDTO.getId())) {
            throw new ApiException("Product not found");
        }
        Product product = ProductDTOMapper.toProduct(productDTO);
        return  productRepository.save(product);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found"));
        return ProductDTOMapper.fromProduct(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found"));
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDTO> getProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .map(ProductDTOMapper::fromProduct);
    }
}
