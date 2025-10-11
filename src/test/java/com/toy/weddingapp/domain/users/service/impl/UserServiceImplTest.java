package com.toy.weddingapp.domain.users.service.impl;

import com.toy.weddingapp.common.jwt.JwtProvider;
import com.toy.weddingapp.domain.users.dto.UserAddRequest;
import com.toy.weddingapp.domain.users.dto.UserLoginRequest;
import com.toy.weddingapp.domain.users.entity.User;
import com.toy.weddingapp.domain.users.mapper.UserMapper;
import com.toy.weddingapp.domain.users.repository.UserJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtProvider jwtProvider;


    @DisplayName("User 저장 성공")
    @Test
    void save_success() {
        // given
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserId("tester");
        userAddRequest.setPassword("tester");
        userAddRequest.setName("김길동");
        userAddRequest.setRole("USER");

        User user = new User();
        user.setId(1);
        user.setUserId("tester");
        user.setPassword("tester");
        user.setName("김길동");
        user.setRole("USER");
        given(userMapper.toEntity(userAddRequest)).willReturn(user);
        given(userJpaRepository.save(user)).willReturn(user);

        // when
        long userId = userService.save(userAddRequest);

        // then
        Assertions.assertThat(userId).isEqualTo(1);
        then(userJpaRepository).should().save(user);
    }

    @DisplayName("로그인 시 JWT 반환")
    @Test
    void login_success() {

        // given
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserId("tester");
        userLoginRequest.setPassword("tester");

        User user = new User();
        user.setId(1);
        user.setUserId("tester");
        user.setPassword("tester");
        user.setName("김길동");
        user.setRole("USER");

        given(userJpaRepository.findByUserIdAndPassword(userLoginRequest.getUserId(), userLoginRequest.getPassword()))
                .willReturn(Optional.of(user));
        given(jwtProvider.generateToken("tester")).willReturn("mocked.jwt.token");


        // when
        String token = userService.login(userLoginRequest);

        // then

        Assertions.assertThat(token).isNotNull();
        Assertions.assertThat(token).isEqualTo("mocked.jwt.token");
    }

    @DisplayName("로그인 실패")
    @Test
    void login_fail() {
        //given

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserId("tester");
        userLoginRequest.setPassword("tester");
        given(userJpaRepository.findByUserIdAndPassword(userLoginRequest.getUserId(), userLoginRequest.getPassword()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.login(userLoginRequest));


    }


}