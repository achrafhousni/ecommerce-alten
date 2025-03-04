package com.alten.shop.service.impl;

import com.alten.shop.domain.dto.CartItemDTO;
import com.alten.shop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final List<CartItemDTO> cart = new ArrayList<>();

    @Override
    public List<CartItemDTO> getCart() {
        return cart;
    }

    @Override
    public CartItemDTO addToCart(CartItemDTO item) {
        cart.add(item);
        return item;
    }

    @Override
    public CartItemDTO updateCartItem(Long productId, int quantity) {
        for (CartItemDTO item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                return item;
            }
        }
        throw new RuntimeException("Product not found in cart");
    }

    @Override
    public void removeCartItem(Long productId) {
        cart.removeIf(item -> item.getProductId().equals(productId));
    }

    @Override
    public void clearCart() {
        cart.clear();
    }
}
