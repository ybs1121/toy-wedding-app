package com.toy.weddingapp.domain.users.controller;

import com.toy.weddingapp.domain.users.dto.UserAddRequest;
import com.toy.weddingapp.domain.users.dto.UserFindRequest;
import com.toy.weddingapp.domain.users.dto.UserLoginRequest;
import com.toy.weddingapp.domain.users.dto.UserResponse;
import com.toy.weddingapp.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long save(@RequestBody UserAddRequest request) {
        return userService.save(request);
    }

    @PostMapping("/login")
    public String loing(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @GetMapping
    public List<UserResponse> searchUsers(UserFindRequest userFindRequest, PageRequest pageRequest) {
        return userService.searchUsers(userFindRequest, pageRequest);
    }
}
