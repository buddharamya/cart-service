package com.microserviceprojects.cart_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="cart")
@Getter
@Setter
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime addedAt = LocalDateTime.now();
}
