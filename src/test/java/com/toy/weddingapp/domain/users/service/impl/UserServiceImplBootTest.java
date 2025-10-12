package com.toy.weddingapp.domain.users.service.impl;

import com.toy.weddingapp.common.jwt.JwtProvider;
import com.toy.weddingapp.domain.users.dto.UserAddRequest;
import com.toy.weddingapp.domain.users.dto.UserFindRequest;
import com.toy.weddingapp.domain.users.dto.UserLoginRequest;
import com.toy.weddingapp.domain.users.dto.UserResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@Transactional
class UserServiceImplBootTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserJpaRepository userJpaRepository;


    @Test
    void 사용자검색_성공() {

        // G
        User user1 = new User();
        user1.setUserId("tester");
        user1.setPassword("tester");
        user1.setName("김길동");
        user1.setRole("USER");

        User user2 = new User();
        user2.setUserId("teest2");
        user2.setPassword("tester2");
        user2.setName("이수근");
        user2.setRole("ADMIN");

        userJpaRepository.save(user1);
        userJpaRepository.save(user2);

        UserFindRequest find = new UserFindRequest();
        find.setName("이수근");

        PageRequest pageRequest = PageRequest.of(0, 10);

        //W
        List<UserResponse> userResponses = userService.searchUsers(find, pageRequest);

        //t
        Assertions.assertThat(userResponses).isNotEmpty();
        Assertions.assertThat(userResponses.size()).isEqualTo(1);
        Assertions.assertThat(userResponses.get(0).getName()).isEqualTo(user2.getName());


    }


}