package com.toy.weddingapp.domain.weddings.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import com.toy.weddingapp.domain.users.entity.User;
import com.toy.weddingapp.domain.users.repository.UserRepository;
import com.toy.weddingapp.domain.weddings.dto.WeddingAddRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingModRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingResponse;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import com.toy.weddingapp.domain.weddings.mapper.WeddingMapper;
import com.toy.weddingapp.domain.weddings.repository.WeddingRepository;
import com.toy.weddingapp.domain.weddings.service.WeddingService;
import com.toy.weddingapp.domain.weddings.service.impl.WeddingServiceImpl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class WeddingServiceImplTest {

    @InjectMocks
    private WeddingServiceImpl weddingService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private WeddingRepository weddingRepository;
    @Mock
    private WeddingMapper weddingMapper;
    @Mock
    private EntityManager em;


    @DisplayName("웨딩정보 저장 성공")
    @Test
    void save_success() {
        //given
        LocalDateTime now = LocalDateTime.now();
        WeddingAddRequest weddingAddRequest = new WeddingAddRequest();
        weddingAddRequest.setUserId("tester");
        weddingAddRequest.setTitle("웨딩정보 저장");
        weddingAddRequest.setLocation("서울시 광진구 ㅇㅇ호텔");
        weddingAddRequest.setWeddingDate(now);
        weddingAddRequest.setGroomName("김길동");
        weddingAddRequest.setBrideName("홍길동");

        User user = new User();
        user.setUserId("tester");

        Weddings weddings = new Weddings();
        weddings.setId(1L);
        weddings.setUser(user);
        weddings.setTitle("웨딩정보 저장");
        weddings.setLocation("서울시 광진구 ㅇㅇ호텔");
        weddings.setWeddingDate(now);
        weddings.setGroomName("김길동");
        weddings.setBrideName("홍길동");

        given(userRepository.findByUserId("tester")).willReturn(Optional.of(user));
        given(weddingMapper.toEntity(weddingAddRequest, user)).willReturn(weddings);
        given(weddingRepository.save(weddings)).willReturn(weddings);


        //when
        long save = weddingService.save(weddingAddRequest);
        //then
        assertThat(save).isEqualTo(1);
        then(userRepository).should(times(1)).findByUserId("tester");
        then(weddingRepository).should(times(1)).save(weddings);
    }

    @DisplayName("웨딩정보 저장 실패")
    @Test
    void save_fail() {
        //given
        LocalDateTime now = LocalDateTime.now();
        WeddingAddRequest weddingAddRequest = new WeddingAddRequest();
        weddingAddRequest.setUserId("tester");
        weddingAddRequest.setTitle("웨딩정보 저장");
        weddingAddRequest.setLocation("서울시 광진구 ㅇㅇ호텔");
        weddingAddRequest.setWeddingDate(now);
        weddingAddRequest.setGroomName("김길동");
        weddingAddRequest.setBrideName("홍길동");


        given(userRepository.findByUserId("tester")).willReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            weddingService.save(weddingAddRequest);
        });

    }

    @DisplayName("웨딩정보 업데이트 성공")
    @Test
    void update_success() {
        // given
        long weddingId = 1L;
        WeddingModRequest modRequest = new WeddingModRequest();
        modRequest.setTitle("Updated Title");
        modRequest.setLocation("Busan");
        modRequest.setWeddingDate(LocalDateTime.now().plusDays(10));
        modRequest.setGroomName("Updated Groom");
        modRequest.setBrideName("Updated Bride");

        Weddings existingWedding = new Weddings();
        existingWedding.setId(weddingId);
        existingWedding.setTitle("Old Title");

        given(weddingRepository.findById(weddingId)).willReturn(Optional.of(existingWedding));

        //when
        long resultId = weddingService.update(weddingId, modRequest);
        // then

        assertThat(resultId).isEqualTo(weddingId);
        assertThat(existingWedding.getTitle()).isEqualTo("Updated Title");
        assertThat(existingWedding.getLocation()).isEqualTo("Busan");
        assertThat(existingWedding.getGroomName()).isEqualTo("Updated Groom");
        assertThat(existingWedding.getBrideName()).isEqualTo("Updated Bride");

        then(weddingRepository).should(times(1)).findById(weddingId);
    }
}