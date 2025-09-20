package com.toy.weddingapp.domain.users.dto;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String userId;
    private String password;
}
