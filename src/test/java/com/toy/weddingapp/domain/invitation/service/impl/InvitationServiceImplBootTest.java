package com.toy.weddingapp.domain.invitation.service.impl;

import com.toy.weddingapp.common.constant.Status;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationStatusStatisticsResponse;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import com.toy.weddingapp.domain.invitation.repository.InvitationJpaRepository;
import com.toy.weddingapp.domain.invitation.service.InvitationService;
import com.toy.weddingapp.domain.users.entity.User;
import com.toy.weddingapp.domain.users.repository.UserJpaRepository;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import com.toy.weddingapp.domain.weddings.repository.WeddingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class InvitationServiceImplBootTest {

    @Autowired
    InvitationService invitationService;

    @Autowired
    InvitationJpaRepository invitationJpaRepository;

    @Autowired
    WeddingRepository weddingRepository;

    @Autowired
    UserJpaRepository userJpaRepository;


    @Test
    void 통계정보를_조회한다() {
        //G
        User user1 = new User();
        user1.setUserId("tester");
        user1.setPassword("tester");
        user1.setName("김길동");
        user1.setRole("USER");

        User user2 = new User();
        user2.setUserId("tester2");
        user2.setPassword("tester2");
        user2.setName("김길동2");
        user2.setRole("USER");


        userJpaRepository.saveAll(List.of(user1, user2));

        Weddings weddings1 = new Weddings();
        weddings1.setUser(user1);
        weddings1.setTitle("웨딩정보 저장");
        weddings1.setLocation("서울시 광진구 ㅇㅇ호텔");
        weddings1.setWeddingDate(LocalDateTime.now());
        weddings1.setGroomName("김길동");
        weddings1.setBrideName("홍길동");

        Weddings weddings2 = new Weddings();
        weddings2.setUser(user2);
        weddings2.setTitle("웨딩정보 저장");
        weddings2.setLocation("서울시 광진구 ㅇㅇ호텔");
        weddings2.setWeddingDate(LocalDateTime.now());
        weddings2.setGroomName("김길동");
        weddings2.setBrideName("홍길동");


        weddingRepository.saveAll(List.of(weddings1, weddings2));

        Invitation invitation1 = new Invitation();
        invitation1.setWeddings(weddings1);
        invitation1.setTitle("title");
        invitation1.setMessage("message");
        invitation1.setStatus(Status.WAITING);
        invitation1.setStartDate(LocalDate.of(2025, 8, 10));
        invitation1.setEndDate(LocalDate.of(2025, 9, 10));

        Invitation invitation2 = new Invitation();

        invitation2.setWeddings(weddings2);
        invitation2.setTitle("title");
        invitation2.setMessage("message");
        invitation2.setStatus(Status.CLOSED);
        invitation2.setStartDate(LocalDate.of(2025, 8, 10));
        invitation2.setEndDate(LocalDate.of(2025, 9, 10));


        invitationJpaRepository.saveAll(List.of(invitation1, invitation2));


        //W
        InvitationStatusStatisticsResponse invitationStatusStatistics = invitationService.getInvitationStatusStatistics();

        //T
        Assertions.assertNotNull(invitationStatusStatistics);
        Assertions.assertEquals(1, invitationStatusStatistics.getClosedCount());
        Assertions.assertEquals(1, invitationStatusStatistics.getWaitingCount());
        Assertions.assertEquals(0, invitationStatusStatistics.getProgressCount());
    }

    @Test
    void 초대장_정보를_조회한다() {
        //G
        User user1 = new User();
        user1.setUserId("tester");
        user1.setPassword("tester");
        user1.setName("김길동");
        user1.setRole("USER");

        User user2 = new User();
        user2.setUserId("tester2");
        user2.setPassword("tester2");
        user2.setName("김길동2");
        user2.setRole("USER");


        userJpaRepository.saveAll(List.of(user1, user2));

        Weddings weddings1 = new Weddings();
        weddings1.setUser(user1);
        weddings1.setTitle("웨딩정보 저장1");
        weddings1.setLocation("서울시 광진구 ㅇㅇ호텔");
        weddings1.setWeddingDate(LocalDateTime.now());
        weddings1.setGroomName("김길동");
        weddings1.setBrideName("홍길동");

        Weddings weddings2 = new Weddings();
        weddings2.setUser(user2);
        weddings2.setTitle("웨딩정보 저장2");
        weddings2.setLocation("서울시 광진구 ㅇㅇ호텔");
        weddings2.setWeddingDate(LocalDateTime.now());
        weddings2.setGroomName("김길동");
        weddings2.setBrideName("홍길동");


        weddingRepository.saveAll(List.of(weddings1, weddings2));

        Invitation invitation1 = new Invitation();
        invitation1.setWeddings(weddings1);
        invitation1.setTitle("title");
        invitation1.setMessage("message");
        invitation1.setStatus(Status.WAITING);
        invitation1.setStartDate(LocalDate.of(2025, 8, 10));
        invitation1.setEndDate(LocalDate.of(2025, 9, 10));

        Invitation invitation2 = new Invitation();

        invitation2.setWeddings(weddings2);
        invitation2.setTitle("title");
        invitation2.setMessage("message");
        invitation2.setStatus(Status.CLOSED);
        invitation2.setStartDate(LocalDate.of(2025, 8, 10));
        invitation2.setEndDate(LocalDate.of(2025, 9, 10));


        invitationJpaRepository.saveAll(List.of(invitation1, invitation2));

        //when
        List<InvitationResponse> invitationResponses = invitationService.searchInvitations();

        //then
        Assertions.assertNotNull(invitationResponses);
        Assertions.assertEquals(2, invitationResponses.size());

        org.assertj.core.api.Assertions.assertThat(invitationResponses).extracting(InvitationResponse::getStatus)
                .containsExactlyInAnyOrder(Status.WAITING, Status.CLOSED);

        org.assertj.core.api.Assertions.assertThat(invitationResponses).extracting("weddings.title")
                .containsExactlyInAnyOrder("웨딩정보 저장1", "웨딩정보 저장2");


    }

}
