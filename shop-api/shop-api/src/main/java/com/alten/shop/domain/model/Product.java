package com.alten.shop.domain.model;

import com.alten.shop.domain.enums.InventoryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private int shellId;
    @Enumerated(EnumType.ORDINAL) // Stores as INSTOCK, LOWSTOCK, OUTOFSTOCK
    @Column(nullable = false)
    private InventoryStatus inventoryStatus;
    private int rating;
    private long createdAt;
    private long updatedAt;

 }

