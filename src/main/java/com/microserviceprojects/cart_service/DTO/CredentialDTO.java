package com.microserviceprojects.cart_service.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialDTO {

    private Integer credentialId;

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    private RoleBasedAuthority roleBasedAuthority;
}
