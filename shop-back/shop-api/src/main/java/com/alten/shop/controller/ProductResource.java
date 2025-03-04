package com.alten.shop.controller;


import com.alten.shop.domain.HttpResponse;
import com.alten.shop.domain.dto.ProductDTO;
import com.alten.shop.domain.model.Product;
import com.alten.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RestController
@RequestMapping(path = "/product")
@RequiredArgsConstructor
@PreAuthorize("@securityService.isAdmin(authentication)")
public class ProductResource {

    private final ProductService productService;
    @GetMapping
    public ResponseEntity<HttpResponse> getProducts(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("products", productService.getProducts(page.orElse(0), size.orElse(10))))
                        .message("Products retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getProduct(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("product", productDTO))
                        .message("Product retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(productDTO);

        return ResponseEntity.created(URI.create("/product/" + createdProduct.getId()))
                .body(HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("product", createdProduct))
                        .message("Product created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductDTO productDTO) {


        Product updatedProduct = productService.updateProduct(productDTO);

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("product", updatedProduct))
                        .message("Product updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpResponse> partialUpdateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        ProductDTO product = productService.getProductById(id);

        if (updates.containsKey("price")) {
            product.setPrice(Double.valueOf(updates.get("price").toString()));
        }
        if (updates.containsKey("quantity")) {
            product.setQuantity(Integer.valueOf(updates.get("quantity").toString()));
        }

        Product updatedProduct = productService.updateProduct(product);

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("product", updatedProduct))
                        .message("Product partially updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Product deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
