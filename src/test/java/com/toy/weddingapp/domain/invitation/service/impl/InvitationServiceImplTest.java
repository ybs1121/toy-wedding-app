package com.toy.weddingapp.domain.invitation.service.impl;

import com.toy.weddingapp.common.constant.Status;
import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationUpdateRequest;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import com.toy.weddingapp.domain.invitation.mapper.InvitationMapper;
import com.toy.weddingapp.domain.invitation.repository.InvitationJpaRepository;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class InvitationServiceImplTest {

    @InjectMocks
    private InvitationServiceImpl invitationServiceImpl;

    @Mock
    private InvitationJpaRepository invitationJpaRepository;

    @Mock
    private InvitationMapper invitationMapper;

    @Mock
    private EntityManager em;

    @Value("${weddings.domain}")
    private String DOMAIN;


    @Test
    void save_success() {
        //given
        InvitationAddRequest invitationAddRequest = new InvitationAddRequest();
        invitationAddRequest.setWeddingsId(1L);
        invitationAddRequest.setTitle("title");
        invitationAddRequest.setMessage("message");
        invitationAddRequest.setStatus(Status.WAITING);
        invitationAddRequest.setStartDate(LocalDate.of(2025, 8, 10));
        invitationAddRequest.setEndDate(LocalDate.of(2025, 9, 10));

        Weddings weddings = new Weddings();
        weddings.setId(1L);

        Invitation invitation = new Invitation();
        invitation.setId(1L);
        invitation.setWeddings(weddings);
        invitation.setTitle("title");
        invitation.setMessage("message");
        invitation.setStatus(Status.WAITING);
        invitation.setStartDate(LocalDate.of(2025, 8, 10));
        invitation.setEndDate(LocalDate.of(2025, 9, 10));

        given(invitationMapper.toEntity(invitationAddRequest, weddings)).willReturn((invitation));
        given(invitationJpaRepository.save(invitation)).willReturn(invitation);
        given(em.getReference(Weddings.class, 1L)).willReturn(weddings);

        //when
        Long saveId = invitationServiceImpl.save(invitationAddRequest);

        //then
        Assertions.assertNotNull(saveId);
        org.assertj.core.api.Assertions.assertThat(saveId).isEqualTo(invitation.getId());

    }

    @Test
    void update_success() {
        //given

        InvitationUpdateRequest invitationUpdateRequest = new InvitationUpdateRequest();
        invitationUpdateRequest.setId(1L);
        invitationUpdateRequest.setTitle("update title");
        invitationUpdateRequest.setMessage("update message");
        invitationUpdateRequest.setStatus(Status.PROGRESS);
        invitationUpdateRequest.setStartDate(LocalDate.of(2025, 9, 10));
        invitationUpdateRequest.setEndDate(LocalDate.of(2025, 10, 10));

        Weddings weddings = new Weddings();
        weddings.setId(1L);

        Invitation invitation = new Invitation();
        invitation.setId(1L);
        invitation.setWeddings(weddings);
        invitation.setTitle("update title");
        invitation.setMessage("update message");
        invitation.setStatus(Status.PROGRESS);
        invitation.setStartDate(LocalDate.of(2025, 9, 10));
        invitation.setEndDate(LocalDate.of(2025, 10, 10));

        Invitation updateInvitation = new Invitation();
        updateInvitation.setId(1L);
        updateInvitation.setWeddings(weddings);
        updateInvitation.setTitle("update title");
        updateInvitation.setMessage("update message");
        updateInvitation.setStatus(Status.PROGRESS);
        updateInvitation.setStartDate(LocalDate.of(2025, 9, 10));
        updateInvitation.setEndDate(LocalDate.of(2025, 10, 10));

        given(invitationJpaRepository.findById(invitation.getId())).willReturn(Optional.of(invitation));
        // when
        Long updateId = invitationServiceImpl.update(invitationUpdateRequest);

        // then
        Assertions.assertNotNull(updateId);
        org.assertj.core.api.Assertions.assertThat(updateId).isEqualTo(updateInvitation.getId());
        Assertions.assertEquals(updateInvitation.getTitle(), invitationUpdateRequest.getTitle());
        Assertions.assertEquals(updateInvitation.getMessage(), invitationUpdateRequest.getMessage());
        Assertions.assertEquals(updateInvitation.getStatus(), invitationUpdateRequest.getStatus());
        Assertions.assertEquals(updateInvitation.getStartDate(), invitationUpdateRequest.getStartDate());
        Assertions.assertEquals(updateInvitation.getEndDate(), invitationUpdateRequest.getEndDate());

    }

    @Test
    void create_uri() {
        // given
        Long id = 10000L;
        Invitation invitation = new Invitation();
        invitation.setId(id);
        invitation.setWeddings(null);
        invitation.setTitle("title");
        invitation.setMessage("message");
        invitation.setStatus(Status.PROGRESS);
        invitation.setStartDate(LocalDate.of(2025, 9, 10));
        invitation.setEndDate(LocalDate.of(2025, 10, 10));
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setId(id);
        invitationResponse.setWeddings(null);
        invitationResponse.setTitle("title");
        invitationResponse.setMessage("message");
        invitationResponse.setStatus(Status.PROGRESS);
        invitationResponse.setStartDate(LocalDate.of(2025, 9, 10));
        invitationResponse.setEndDate(LocalDate.of(2025, 10, 10));


        given(invitationJpaRepository.findById(invitation.getId())).willReturn(Optional.of(invitation));
        given(invitationMapper.toDto(invitation)).willReturn(invitationResponse);

        // when
        InvitationResponse invitationRes = invitationServiceImpl.createUrl(id);
        // then
        Assertions.assertNotNull(invitationRes);
        Assertions.assertEquals(invitation.getId(), invitationRes.getId());
        Assertions.assertEquals(invitation.getTitle(), invitationRes.getTitle());
        Assertions.assertEquals(invitation.getMessage(), invitationRes.getMessage());
        Assertions.assertEquals(invitation.getStartDate(), invitationRes.getStartDate());

    }
}