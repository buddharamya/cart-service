package com.microserviceprojects.cart_service.Mapper;

import com.microserviceprojects.cart_service.DTO.CartItemRequestDTO;
import com.microserviceprojects.cart_service.DTO.CartItemResponseDTO;
import com.microserviceprojects.cart_service.Entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CartItemMapper {

    CartItemEntity toEntity(CartItemRequestDTO request);
    CartItemResponseDTO toDTO(CartItemEntity cart);
}
