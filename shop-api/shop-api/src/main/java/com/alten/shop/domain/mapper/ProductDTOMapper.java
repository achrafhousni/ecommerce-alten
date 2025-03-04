package com.alten.shop.domain.mapper;

import com.alten.shop.domain.dto.ProductDTO;
import com.alten.shop.domain.dto.UserDTO;
import com.alten.shop.domain.model.Product;
import com.alten.shop.domain.model.User;
import org.springframework.beans.BeanUtils;

public class ProductDTOMapper {


    public static ProductDTO fromProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }



    public static Product toProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
