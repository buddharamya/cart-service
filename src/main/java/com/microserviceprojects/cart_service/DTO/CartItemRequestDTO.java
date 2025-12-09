package com.microserviceprojects.cart_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequestDTO {

    private Long userId;
    private Long productId;
    private Integer quantity;
}
