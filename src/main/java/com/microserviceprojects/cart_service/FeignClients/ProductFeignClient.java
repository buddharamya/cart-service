package com.microserviceprojects.cart_service.FeignClients;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(path="/products",url = "http://localhost:8041")  without Eureka
@FeignClient(name="product-service",path="/products")   // with Eureka
@LoadBalancerClient
public interface ProductFeignClient {

    @GetMapping("/exists/{productId}")
    public boolean isProductExists(@PathVariable Long productId);
}
