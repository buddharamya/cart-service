package com.microserviceprojects.cart_service.Repository;

import com.microserviceprojects.cart_service.Entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {

    Optional<CartItemEntity> findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserIdAndProductId(Long userId,Long productId);

    void deleteByUserId(Long userId);

    List<CartItemEntity> findByUserId(Long userId);


}
