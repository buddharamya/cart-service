package com.microserviceprojects.cart_service.FeignClients;

import com.microserviceprojects.cart_service.DTO.UserDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service",path="/users")
@LoadBalancerClient
public interface UserFeignClient {

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer userId);
}
