package com.alten.shop.service.impl;

import com.alten.shop.domain.dto.WishlistItemDTO;
import com.alten.shop.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final List<WishlistItemDTO> wishlist = new ArrayList<>();

    @Override
    public List<WishlistItemDTO> getWishlist() {
        return wishlist;
    }

    @Override
    public WishlistItemDTO addToWishlist(WishlistItemDTO item) {
        wishlist.add(item);
        return item;
    }

    @Override
    public void removeWishlistItem(Long productId) {
        wishlist.removeIf(item -> item.getProductId().equals(productId));
    }

    @Override
    public void clearWishlist() {
        wishlist.clear();
    }
}
