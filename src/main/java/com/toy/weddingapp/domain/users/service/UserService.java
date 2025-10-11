package com.toy.weddingapp.domain.users.service;

import com.toy.weddingapp.domain.users.dto.UserAddRequest;
import com.toy.weddingapp.domain.users.dto.UserFindRequest;
import com.toy.weddingapp.domain.users.dto.UserLoginRequest;
import com.toy.weddingapp.domain.users.dto.UserResponse;

import java.util.List;

public interface UserService {

    Long save(UserAddRequest request);

    String login(UserLoginRequest request);

    List<UserResponse> searchUsers(UserFindRequest userFindRequest);
}
