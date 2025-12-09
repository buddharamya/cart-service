package com.microserviceprojects.cart_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemResponseDTO {

    private Integer id;
    private Long userId;
    private Long productId;
    private Integer quantity;
}
