package com.toy.weddingapp.domain.users.dto;

import lombok.Data;

@Data
public class UserFindRequest {


    private String userId;

    private String name;

    private String role;

}
