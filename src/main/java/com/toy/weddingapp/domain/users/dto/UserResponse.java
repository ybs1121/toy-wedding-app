package com.toy.weddingapp.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {


    private String userId;


    private String name;

    private String role;


    @AllArgsConstructor
    public static class Results {
        private List<UserResponse> users;
        private long count;
    }
}
