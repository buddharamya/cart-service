package com.microserviceprojects.cart_service.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public boolean checkProductExists(Long productId) {


        ResponseEntity<Boolean> response = restTemplate.exchange(
                productServiceUrl,
                HttpMethod.GET,
                null,
                Boolean.class,
                productId // this replaces {productId} in the URL

        );

        return response.getBody();  // true / false
    }
}
