package com.microserviceprojects.cart_service.Service;

import com.microserviceprojects.cart_service.DTO.CartItemRequestDTO;
import com.microserviceprojects.cart_service.DTO.CartItemResponseDTO;

import java.util.List;

public interface CartService {

    CartItemResponseDTO addToCart(CartItemRequestDTO request);
    CartItemResponseDTO updateQuantity(CartItemRequestDTO request);
    void removeItem(Long userId,Long productId);
    List<CartItemResponseDTO> getUserCart(Long userId);
    void clearCart(Long userId);
}
