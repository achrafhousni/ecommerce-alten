package com.alten.shop.service;

import com.alten.shop.domain.dto.WishlistItemDTO;
import java.util.List;

public interface WishlistService {
    List<WishlistItemDTO> getWishlist();
    WishlistItemDTO addToWishlist(WishlistItemDTO item);
    void removeWishlistItem(Long productId);
    void clearWishlist();
}
