package com.toy.weddingapp.domain.users.service;

import com.toy.weddingapp.domain.users.dto.UserAddRequest;
import com.toy.weddingapp.domain.users.dto.UserLoginRequest;

public interface UserService {

    Long save(UserAddRequest request);

    String login(UserLoginRequest request);
}
