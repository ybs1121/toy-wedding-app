package com.toy.weddingapp.domain.users.service.impl;

import com.toy.weddingapp.common.jwt.JwtProvider;
import com.toy.weddingapp.domain.users.dto.UserAddRequest;
import com.toy.weddingapp.domain.users.dto.UserLoginRequest;
import com.toy.weddingapp.domain.users.entity.User;
import com.toy.weddingapp.domain.users.mapper.UserMapper;
import com.toy.weddingapp.domain.users.repository.UserRepository;
import com.toy.weddingapp.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtProvider jwtProvider;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long save(UserAddRequest request) {
        User saveUser = userRepository.save(userMapper.toEntity(request));
        return saveUser.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public String login(UserLoginRequest request) {
        User findUser = userRepository.findByUserIdAndPassword(request.getUserId(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return jwtProvider.generateToken(findUser.getUserId());
    }
}
