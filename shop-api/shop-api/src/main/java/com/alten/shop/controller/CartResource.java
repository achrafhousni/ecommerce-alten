package com.alten.shop.controller;


import com.alten.shop.domain.dto.CartItemDTO;
import com.alten.shop.domain.HttpResponse;
import com.alten.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartResource {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<HttpResponse> getCart() {
        List<CartItemDTO> cart = cartService.getCart();
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .data(of("cart", cart))
                .message("Cart retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PostMapping
    public ResponseEntity<HttpResponse> addToCart(@RequestBody CartItemDTO item) {
        CartItemDTO added = cartService.addToCart(item);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .data(of("item", added))
                .message("Item added to cart")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<HttpResponse> updateCartItem(@PathVariable Long productId, @RequestParam int quantity) {
        CartItemDTO updated = cartService.updateCartItem(productId, quantity);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .data(of("item", updated))
                .message("Cart item updated")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<HttpResponse> removeCartItem(@PathVariable Long productId) {
        cartService.removeCartItem(productId);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .message("Item removed from cart")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<HttpResponse> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .message("Cart cleared")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }
}
