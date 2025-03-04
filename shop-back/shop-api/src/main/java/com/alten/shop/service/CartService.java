package com.alten.shop.service;

import com.alten.shop.domain.dto.CartItemDTO;
import java.util.List;

public interface CartService {
    List<CartItemDTO> getCart();
    CartItemDTO addToCart(CartItemDTO item);
    CartItemDTO updateCartItem(Long productId, int quantity);
    void removeCartItem(Long productId);
    void clearCart();
}
