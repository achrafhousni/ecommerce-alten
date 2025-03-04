package com.alten.shop.domain.dto;


import com.alten.shop.domain.enums.InventoryStatus;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private InventoryStatus inventoryStatus;
    private int rating;
}
