package com.microserviceprojects.cart_service.Service;

import com.microserviceprojects.cart_service.DTO.CartItemRequestDTO;
import com.microserviceprojects.cart_service.DTO.CartItemResponseDTO;
import com.microserviceprojects.cart_service.DTO.UserDTO;
import com.microserviceprojects.cart_service.Entity.CartItemEntity;
import com.microserviceprojects.cart_service.Exception.ProductNotFoundException;
import com.microserviceprojects.cart_service.Exception.UserNotFoundException;
import com.microserviceprojects.cart_service.FeignClients.ProductFeignClient;
import com.microserviceprojects.cart_service.FeignClients.UserFeignClient;
import com.microserviceprojects.cart_service.Mapper.CartItemMapper;
import com.microserviceprojects.cart_service.Repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Override
    public CartItemResponseDTO addToCart(CartItemRequestDTO request) {

        // TODO : 1. product Id exists in DB or not

        checkProductExistsUsingFeign(request.getProductId());
        var userDTO = checkUserExistsUsingFeign(request.getUserId());
//        checkUserExists(request.getUserId());
//        checkProductExists(request.getProductId());
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
        var dto = cartItemMapper.toDTO(saved);
        dto.setUserDTO(userDTO);
        return dto;
    }

    private UserDTO checkUserExistsUsingFeign(Long userId) {

        ResponseEntity<UserDTO> responseEntity = userFeignClient.findById(userId.intValue());

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            UserDTO result = responseEntity.getBody();
            return result;
        }
        else
        {
          throw new UserNotFoundException("User Not Exists in DB: " + userId);
        }
    }

    private void checkProductExistsUsingFeign(Long productId) {

        boolean productExists = productFeignClient.isProductExists(productId);
        if(!productExists) {
            throw new ProductNotFoundException("Product Not Exists in DB: "+ productId);
        }

    }

    public boolean checkUserExists(Long userId) {
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(userServiceUrl,Boolean.class,userId);

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            Boolean isUserExists = responseEntity.getBody();

            if (!isUserExists) {
                throw new UserNotFoundException("User Not Exists in DB: " + userId);
            }

            return true;
        }

        return true;
    }

    public boolean checkProductExists(Long productId) {

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(productServiceUrl,Boolean.class,productId);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            Boolean isProductExists = responseEntity.getBody();

            if(!isProductExists) {
                throw new ProductNotFoundException("Product Not Exists in DB: "+ productId);
            }

            return true;
        }
        return true;
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
