package com.alten.shop.repository;

import com.alten.shop.domain.model.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, ListCrudRepository<Product, Long> {


}
