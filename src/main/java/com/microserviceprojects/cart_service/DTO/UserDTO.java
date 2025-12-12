package com.microserviceprojects.cart_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String emailAddress;    // I don't want to expose the same as the real world Entity
    private String contact;

    private CredentialDTO credential;
}
