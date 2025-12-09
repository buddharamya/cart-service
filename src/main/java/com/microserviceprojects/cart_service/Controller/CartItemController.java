package com.microserviceprojects.cart_service.Controller;

import com.microserviceprojects.cart_service.Client.ProductClient;
import com.microserviceprojects.cart_service.DTO.CartItemRequestDTO;
import com.microserviceprojects.cart_service.DTO.CartItemResponseDTO;
import com.microserviceprojects.cart_service.Service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartItemController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductClient productClient;

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@RequestBody CartItemRequestDTO request) {
        log.info("CartItemController addItemToCart");
        CartItemResponseDTO response= cartService.addToCart(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(@RequestBody CartItemRequestDTO request) {
        log.info("CartItemController updateCartItem");
        CartItemResponseDTO response= cartService.updateQuantity(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartByUserId(@PathVariable Long userId) {
        log.info("CartItemController getCartByUserId");
        List<CartItemResponseDTO> response = cartService.getUserCart(userId);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long productId, @RequestParam Long userId) {
        log.info("CartItemController removeItemFromCart");
        cartService.removeItem(userId,productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearUserCart(@PathVariable Long userId) {
        log.info("CartItemController clearUserCart");
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/{productId}")
    public boolean checkProductExists(@PathVariable Long productId) {
        log.info("CartItemController checkProductExists");
        return productClient.checkProductExists(productId);
    }
}
