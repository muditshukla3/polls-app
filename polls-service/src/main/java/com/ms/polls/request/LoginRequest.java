package com.ms.polls.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String userOrEmail;

    @NotBlank
    private String password;
}
