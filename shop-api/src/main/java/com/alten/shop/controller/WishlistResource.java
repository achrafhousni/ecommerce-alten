package com.alten.shop.controller;

import com.alten.shop.domain.dto.WishlistItemDTO;
import com.alten.shop.domain.HttpResponse;
import com.alten.shop.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistResource {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<HttpResponse> getWishlist() {
        List<WishlistItemDTO> wishlist = wishlistService.getWishlist();
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .data(of("wishlist", wishlist))
                .message("Wishlist retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PostMapping
    public ResponseEntity<HttpResponse> addToWishlist(@RequestBody WishlistItemDTO item) {
        WishlistItemDTO added = wishlistService.addToWishlist(item);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .data(of("item", added))
                .message("Item added to wishlist")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<HttpResponse> removeWishlistItem(@PathVariable Long productId) {
        wishlistService.removeWishlistItem(productId);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .message("Item removed from wishlist")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<HttpResponse> clearWishlist() {
        wishlistService.clearWishlist();
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .message("Wishlist cleared")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }
}
