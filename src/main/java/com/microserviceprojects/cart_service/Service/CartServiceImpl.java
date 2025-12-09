package com.microserviceprojects.cart_service.Service;

import com.microserviceprojects.cart_service.DTO.CartItemRequestDTO;
import com.microserviceprojects.cart_service.DTO.CartItemResponseDTO;
import com.microserviceprojects.cart_service.Entity.CartItemEntity;
import com.microserviceprojects.cart_service.Mapper.CartItemMapper;
import com.microserviceprojects.cart_service.Repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public CartItemResponseDTO addToCart(CartItemRequestDTO request) {

        // TODO : 1. product Id exists in DB or not
        CartItemEntity item;
        Optional<CartItemEntity> existingItem = cartItemRepository.findByUserIdAndProductId(request.getUserId(),request.getProductId());

        if(existingItem.isPresent()) {
            item = existingItem.get();
            item.setQuantity(item.getQuantity()+request.getQuantity());
        }
        else {
//            item = new CartItemEntity();
            item = cartItemMapper.toEntity(request);
        }

        CartItemEntity saved = cartItemRepository.save(item);
        return cartItemMapper.toDTO(saved);
    }

    @Override
    public CartItemResponseDTO updateQuantity(CartItemRequestDTO request) {
        CartItemEntity existingItem =
                cartItemRepository.findByUserIdAndProductId(request.getUserId(),request.getProductId())
                        .orElseThrow(() -> new RuntimeException("Item not in cart"));

        existingItem.setQuantity(request.getQuantity());
        return cartItemMapper.toDTO(cartItemRepository.save(existingItem));
    }

    @Override
    @Transactional
    public void removeItem(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId,productId);
    }

    @Override
    @Transactional
    public List<CartItemResponseDTO> getUserCart(Long userId) {
        List<CartItemResponseDTO> items = cartItemRepository.findByUserId(userId)
                .stream()
                .map(cartItemMapper::toDTO)
                .toList();

        if (items.isEmpty()) {
            throw new RuntimeException("Items not in user cart");
        }
        return items;
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    // Product Id exists in DB or not using Rest Template, we need to do from cart ms to product ms


}
